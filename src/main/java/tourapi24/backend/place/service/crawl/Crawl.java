package tourapi24.backend.place.service.crawl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.place.domain.GovContentTypeId;
import tourapi24.backend.place.domain.Place;
import tourapi24.backend.place.dto.external.gov.AreaBasedListResponse;
import tourapi24.backend.place.dto.external.kakao.KakaoLocalResponse;
import tourapi24.backend.place.dto.external.kakao.KakaoPlaceResponse;
import tourapi24.backend.place.repository.PlaceRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class Crawl {

    private static final String GOV_AREA_BASED_LIST_URL = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=%d&MobileOS=ETC&MobileApp=OH&_type=json&contentTypeId=&areaCode=6&serviceKey=%s";
    private static final String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s";
    private static final String KAKAO_PLACE_URL = "https://place.map.kakao.com/main/v/%d";

    private final PlaceRepository placeRepository;
    private final RestTemplate restTemplate;

    @Value("${key.gov}")
    private String govKey;

    @Value("${key.kakao}")
    private String kakaoKey;

    @Transactional
    public void fetch(int limit) {
        List<AreaBasedListResponse.Item> items = fetchAreaBasedList(limit);

        for (AreaBasedListResponse.Item item : items) {
            if (!isValidContentType(item)) {
                log.debug("Skip course");
                continue;
            }

            try {
                KakaoLocalResponse kakaoLocalResponse = fetchKakaoPlaceId(item.getTitle());
                Long kakaoPlaceId = extractKakaoPlaceId(kakaoLocalResponse);
                log.debug("Kakao Place ID: {}", kakaoPlaceId);

                KakaoPlaceResponse kakaoPlaceResponse = fetchKakaoCongestion(kakaoPlaceId);
                String address = determineAddress(kakaoLocalResponse);

                Place place = createPlace(item, kakaoPlaceId, kakaoPlaceResponse, address);
                placeRepository.save(place);
            } catch (Exception e) {
                log.error("Error processing item: {}", item.getTitle(), e);
            }
        }
    }

    private List<AreaBasedListResponse.Item> fetchAreaBasedList(int limit) {
        String url = String.format(GOV_AREA_BASED_LIST_URL, limit, govKey);
        AreaBasedListResponse response = restTemplate.getForObject(url, AreaBasedListResponse.class);
        if (response == null ||
                response.getResponse() == null ||
                response.getResponse().getBody() == null
        ) {
            throw new IllegalStateException("Failed to fetch areaBasedList1");
        }
        return response.getResponse().getBody().getItems().getItem();
    }

    private boolean isValidContentType(AreaBasedListResponse.Item item) {
        return Integer.parseInt(item.getContenttypeid()) != GovContentTypeId.여행코스.getId();
    }

    private KakaoLocalResponse fetchKakaoPlaceId(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoKey);
        String url = String.format(KAKAO_LOCAL_SEARCH_URL, title);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                KakaoLocalResponse.class).getBody();
    }

    private Long extractKakaoPlaceId(KakaoLocalResponse response) {
        if (response == null || response.getDocuments().isEmpty()) {
            throw new IllegalStateException("No Kakao place found");
        }
        String placeUrl = response.getDocuments().getFirst().getPlaceUrl();
        return Long.parseLong(placeUrl.split("/")[3]);
    }

    private KakaoPlaceResponse fetchKakaoCongestion(Long kakaoPlaceId) {
        String url = String.format(KAKAO_PLACE_URL, kakaoPlaceId);
        return restTemplate.getForObject(url, KakaoPlaceResponse.class);
    }

    private String determineAddress(KakaoLocalResponse response) {
        KakaoLocalResponse.Document document = response.getDocuments().getFirst();
        return document.getRoadAddressName().isEmpty() ? document.getAddressName() : document.getRoadAddressName();
    }

    private Place createPlace(AreaBasedListResponse.Item item, Long kakaoPlaceId, KakaoPlaceResponse kakaoPlaceResponse, String address) {
        Place.PlaceBuilder builder = Place.builder()
                .contentId(Long.parseLong(item.getContentid()))
                .kakaoId(kakaoPlaceId)
                .contentTypeId(GovContentTypeId.fromId(Integer.parseInt(item.getContenttypeid())))
                .title(item.getTitle())
                .tel(kakaoPlaceResponse.getBasicInfo().getPhonenum())
                .address(address);

        Place place;
        if (kakaoPlaceResponse.getS2graph() != null) {
            // 혼잡도 데이터 존재하는 경우
            builder.isCongestion(true);
            place = builder.build();
            place.setCongestionData(
                    kakaoPlaceResponse.getS2graph().getDay().getMax(),
                    kakaoPlaceResponse.getS2graph().getDay().getAvg()
            );
        } else {
            // 혼잡도 데이터 없는 경우
            place = builder.isCongestion(false).build();
        }

        if (item.getFirstimage() != null && !item.getFirstimage().isEmpty()) {
            place.addImage(item.getFirstimage());
        }

        return place;
    }
}
