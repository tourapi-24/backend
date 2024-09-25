package tourapi24.backend.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.place.domain.BusanGu;
import tourapi24.backend.place.domain.GovContentType;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.dto.PlaceDetailResponse;
import tourapi24.backend.place.dto.PlaceRecommendationResponse;
import tourapi24.backend.place.dto.external.kakao.KakaoCoordResponse;
import tourapi24.backend.place.repository.PlaceRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private static final String KAKAO_COORD_URL = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=%f&y=%f";

    private final RestTemplate restTemplate;
    private final PlaceRepository placeRepository;

    @Value("${key.kakao}")
    private String kakaoKey;

    public PlaceRecommendationResponse recommendPlaces(String contentType, double x, double y) {
        int currentHour = getCurrentHour();
        BusanGu busanGu = getBusanGuByLocation(x, y);
        List<Place> places = placeRepository.findPlacesByBusanGuAndContentTypeOrderByCongestion(
                busanGu,
                GovContentType.valueOf(contentType),
                currentHour
        );

        List<PlaceRecommendationResponse.Place> responsePlaces = places.stream()
                .map(place -> PlaceRecommendationResponse.Place.builder()
                        .title(place.getTitle())
                        .contentType(place.getContentType())
                        .imageUrl(place.getImages().stream().findFirst().orElse(null))
                        .congestionLevel(calculateCongestionLevel(getCurrentCongestion(place, currentHour)))
                        .build())
                .collect(Collectors.toList());

        return PlaceRecommendationResponse.builder()
                .gu(busanGu)
                .places(responsePlaces)
                .build();
    }

    public PlaceDetailResponse getPlaceDetail(Long placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow();

        return PlaceDetailResponse.builder()
                .title(place.getTitle())
                .contentType(place.getContentType())
                .imageUrls(place.getImages())
                .congestionLevel(calculateCongestionLevel(getCurrentCongestion(place, getCurrentHour())))
                .address(place.getAddress())
                .build();
    }

    private int getCurrentHour() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).getHour();
    }

    private Float getCurrentCongestion(Place place, int currentHour) {
        return switch (currentHour) {
            case 0 -> place.getCongestion_00();
            case 1 -> place.getCongestion_01();
            case 2 -> place.getCongestion_02();
            case 3 -> place.getCongestion_03();
            case 4 -> place.getCongestion_04();
            case 5 -> place.getCongestion_05();
            case 6 -> place.getCongestion_06();
            case 7 -> place.getCongestion_07();
            case 8 -> place.getCongestion_08();
            case 9 -> place.getCongestion_09();
            case 10 -> place.getCongestion_10();
            case 11 -> place.getCongestion_11();
            case 12 -> place.getCongestion_12();
            case 13 -> place.getCongestion_13();
            case 14 -> place.getCongestion_14();
            case 15 -> place.getCongestion_15();
            case 16 -> place.getCongestion_16();
            case 17 -> place.getCongestion_17();
            case 18 -> place.getCongestion_18();
            case 19 -> place.getCongestion_19();
            case 20 -> place.getCongestion_20();
            case 21 -> place.getCongestion_21();
            case 22 -> place.getCongestion_22();
            case 23 -> place.getCongestion_23();
            default -> throw new IllegalArgumentException("Invalid hour: " + currentHour);
        };
    }

    private Integer calculateCongestionLevel(Float congestion) {
        if (congestion == null) {  // 혼잡도 정보가 없는 경우
            return null;
        } else if (congestion < 0.3) {
            return 0; // 여유
        } else if (congestion < 0.6) {
            return 1; // 보통
        } else {
            return 2; // 혼잡
        }
    }

    private BusanGu getBusanGuByLocation(double x, double y) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoKey);

        String url = String.format(KAKAO_COORD_URL, x, y);

        KakaoCoordResponse response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoCoordResponse.class
            ).getBody();
        } catch (Exception e) {
            return BusanGu.getRandomBusanGu();
        }

        if (response == null || response.getDocuments().isEmpty()) {
            return BusanGu.getRandomBusanGu();
        }

        return BusanGu.getBusanGuByGuName(
                response
                        .getDocuments()
                        .getFirst()
                        .getRegion2depthName()
        );
    }
}
