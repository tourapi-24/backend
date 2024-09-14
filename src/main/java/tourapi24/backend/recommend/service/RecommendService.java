package tourapi24.backend.recommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.recommend.dto.TouristSpot;
import tourapi24.backend.recommend.repository.TouristSpotRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Value("${api.tour}")
    private String tourApi;
    private static double curX;
    private static double curY;
    private static final int RECOMMENDATIONS_COUNT = 5;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private TouristSpotRepository touristSpotRepository;

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2);
    }

    public List<TouristSpot> recommendByAgeGenderLoc(AgeRange age, Gender gender, Double lat, Double lot) {
        try {
            String url = getUrlAgeGenderLoc(age, gender);

            String response = fetchApiResponse(url);
            List<Map<String, Object>> spots = parseApiResponse(response);
            return findNearestSpots(spots);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String getUrlAgeGenderLoc(AgeRange age, Gender gender) {
        String baseUrl = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=12&pageNo=1&MobileOS=ETC&MobileApp=AppTest&ServiceKey="
                + tourApi + "&listYN=Y&arrange=A&contentTypeId=&areaCode=6&_type=json&sigunguCode=";

        // 연령대에 따른 카테고리 선택
        String cat1 = "";
        String cat2 = "";
        String cat3 = "";

        switch(age) {
            case TEEN:
                cat1 = "A02"; // 레포츠, 체험 관광지
                cat2 = "A0201"; // 역사 관광지
                break;
            case TWENTY:
                cat1 = "A02"; // 체험 관광지
                cat2 = "A0202";  // 축제/공연/행사
                break;
            case THIRTY:
                cat1 = "A01";    // 자연 관광지
                cat2 = "A0203"; // 체험 관광지
                break;
            case FORTY:
                cat1 = "A01";    // 자연 관광지
                cat2 = "A0202"; // 휴양 관광지
                cat3 = "A0201"; // 역사 관광지
                break;
            case FIFTY:
                cat1 = "A01";    // 자연 관광지
                // 쇼핑 관련 카테고리 필요시 추가
                break;
        }

        // URL에 카테고리 값 추가
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (!cat1.isEmpty()) urlBuilder.append("&cat1=").append(cat1);
        if (!cat2.isEmpty()) urlBuilder.append("&cat2=").append(cat2);
        if (!cat3.isEmpty()) urlBuilder.append("&cat3=").append(cat3);

        String finalUrl = urlBuilder.toString();

        return finalUrl;
    }

    public List<TouristSpot> recommendByLocation(Double lat, Double lot) {
        try {
            String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=60&MobileOS=ETC&MobileApp=AppTest&ServiceKey="
                    + tourApi + "&listYN=Y&arrange=A&contentTypeId=&areaCode=6&sigunguCode=&cat1=A02&cat2=A0206&cat3=&_type=json";

            String response = fetchApiResponse(url);
            List<Map<String, Object>> spots = parseApiResponse(response);
            return findNearestSpots(spots);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<TouristSpot> recommendByDensity() throws URISyntaxException, JsonProcessingException {
        String url = "https://apis.data.go.kr/6260000/BusanITSAVI/AVIList?serviceKey=" + tourApi + "&pageNo=1&numOfRows=100";
        String response = fetchApiResponse(url);
        List<Map<String, Object>> spots = parseDensityApiResponse(response);

        List<Map<String, Object>> lowDensitySpots = spots.stream()
                .sorted(Comparator.comparingDouble(spot -> Double.parseDouble(spot.get("vol").toString())))
                .limit(RECOMMENDATIONS_COUNT)
                .collect(Collectors.toList());

        List<TouristSpot> recommendations = new ArrayList<>();
        for (Map<String, Object> spot : lowDensitySpots) {
            Double lat = (Double) spot.get("lat");
            Double lot = (Double) spot.get("lot");
            List<TouristSpot> nearbySpots = recommendByLocation(lat, lot);
            if (!nearbySpots.isEmpty()) {
                Random rand = new Random();
                int randomIndex = rand.nextInt(nearbySpots.size());
                TouristSpot nearestSpot = nearbySpots.get(randomIndex);
                recommendations.add(nearestSpot);
            }
        }

        return recommendations;
    }

    private String fetchApiResponse(String url) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(new URI(url), String.class);
    }

    private List<Map<String, Object>> parseApiResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(response, Map.class);
        return ((List<Map<String, Object>>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) responseMap.get("response"))
                .get("body")).get("items")).get("item"));
    }

    private List<Map<String, Object>> parseDensityApiResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, Map.class);
        Map<String, Object> responseMap = (Map<String, Object>) map.get("content");
        return (List<Map<String, Object>>) responseMap.get("items");
    }

    private List<TouristSpot> findNearestSpots(List<Map<String, Object>> spots) {
        return spots.stream()
                .map(this::createTouristSpot)
                .sorted(Comparator.comparingDouble(TouristSpot::getDistance))
                .limit(RECOMMENDATIONS_COUNT)
                .collect(Collectors.toList());
    }

    private TouristSpot createTouristSpot(Map<String, Object> spot) {
        double distance = calculateDistance(
                curX, curY,
                Double.parseDouble((String) spot.get("mapx")),
                Double.parseDouble((String) spot.get("mapy"))
        );
        return new TouristSpot(distance, (String) spot.get("title"), Double.parseDouble((String) spot.get("mapy")), Double.parseDouble((String) spot.get("mapx")), (String) spot.get("cat1"), (String) spot.get("cat2"), (String) spot.get("cat3"), (String) spot.get("firstimage"));
    }

    @PostConstruct
    public void init() {
        try {
            fetchAndSaveTouristSpots();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchAndSaveTouristSpots() throws URISyntaxException, JsonProcessingException {
        String url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=2300&MobileOS=ETC&MobileApp=AppTest&ServiceKey="
                + tourApi +
                "&listYN=Y&arrange=A&contentTypeId=&areaCode=6&sigunguCode=&_type=json";

        String response = restTemplate.getForObject(new URI(url), String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(response, Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) responseMap.get("response")).get("body")).get("items")).get("item");

        for (Map<String, Object> item : items) {
            double distance = calculateDistance(
                    curX, curY,
                    Double.parseDouble((String) item.get("mapx")),
                    Double.parseDouble((String) item.get("mapy"))
            );
            String title = (String) item.get("title");
            String cat1 = (String) item.get("cat1");
            String cat2 = (String) item.get("cat2");
            String cat3 = (String) item.get("cat3");
            Double mapY = Double.parseDouble((String) item.get("mapy"));
            Double mapX = Double.parseDouble((String) item.get("mapx"));
            String firstImage = (String) item.get("firstimage");

            TouristSpot touristSpot = new TouristSpot(distance, title, mapY, mapX, cat1, cat2, cat3, firstImage);
            touristSpotRepository.save(touristSpot);
        }
    }
}
