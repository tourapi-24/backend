package tourapi24.backend.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.place.dto.PlaceRecommendationRequest;
import tourapi24.backend.place.dto.PlaceRecommendationResponse;
import tourapi24.backend.place.service.PlaceService;

@SpringBootTest
@Transactional
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    void 명소추천() {
        // given
        // when
        // then
        PlaceRecommendationRequest request = PlaceRecommendationRequest.builder()
                .x(126.9783882)
                .y(37.5666103)
                .radiusMeter(1000)
                .build();
        PlaceRecommendationResponse response = placeService.recommendPlaces(request, "문화시설", 0, 10);
        response.getPlaces().forEach(System.out::println);
    }
}
