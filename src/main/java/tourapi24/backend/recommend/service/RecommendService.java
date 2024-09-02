package tourapi24.backend.recommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.recommend.dto.TouristSpot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Value("${tour-api}")
    private String tourApi;
    private static double curX;
    private static double curY;
    private static final int RECOMMENDATIONS_COUNT = 5;

    private static double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2);
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
        return new TouristSpot(distance, (String) spot.get("title"), Double.parseDouble((String) spot.get("mapy")), Double.parseDouble((String) spot.get("mapx")));
    }
}
