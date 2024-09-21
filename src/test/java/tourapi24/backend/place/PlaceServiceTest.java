package tourapi24.backend.place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
        placeService.recommendPlaces("관광지", 129.0598, 35.1447).forEach(System.out::println);
    }
}
