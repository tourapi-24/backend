package tourapi24.backend.recommend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.recommend.dto.TouristSpot;
import tourapi24.backend.recommend.service.RecommendService;

import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth/tourist-spots")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/recommend")
    public List<TouristSpot> recommendTouristSpots(
            @RequestParam(required = false) AgeRange age,
            @RequestParam(required = false) Gender gender,
            @RequestParam Double lat,
            @RequestParam Double lot,
            @RequestParam boolean isInBusan
    ) throws URISyntaxException, JsonProcessingException {
        List<TouristSpot> recommendedSpots;

        // TODO
        if (age != null && gender != null) { // 나이와 성별이 반영된 경우

            if (isInBusan) {
                recommendedSpots = recommendService.recommendByAgeGenderLoc(age, gender, lat, lot);
            }
             else {
                recommendedSpots = recommendService.recommendByAgeGenderLoc(age, gender, lat, lot);
            }

        } else {    // 나이와 성별이 반영되지 않은 경우

            if (isInBusan) {    // 위치 기반 랜덤 추천
                recommendedSpots = recommendService.recommendByLocation(lat, lot);
            } else {            // 인구 밀집도 기반 랜덤 추천
                recommendedSpots = recommendService.recommendByDensity();
            }

        }

        return recommendedSpots;
    }
}
