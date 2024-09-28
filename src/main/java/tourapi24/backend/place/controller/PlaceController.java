package tourapi24.backend.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.place.dto.PlaceDetailResponse;
import tourapi24.backend.place.dto.PlaceRecommendationRequest;
import tourapi24.backend.place.dto.PlaceRecommendationResponse;
import tourapi24.backend.place.service.PlaceService;
import tourapi24.backend.relationship.memberlikeplace.MemberLikePlaceService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@Tag(name = "Place")
public class PlaceController {

    private final PlaceService placeService;
    private final MemberLikePlaceService memberLikePlaceService;

    @PostMapping("/recommendation")
    @Operation(
            summary = "혼잡도 기반으로 명소를 추천합니다",
            description = "congestion_level 0, 1, 2는 각각 여유, 보통, 혼잡을 나타냅니다",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PlaceRecommendationResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<PlaceRecommendationResponse> recommendPlaces(
            @RequestBody @Valid PlaceRecommendationRequest request,
            @Parameter(description = "관광지, 문화시설, 축제공연행사, 레포츠, 음식점")
            @RequestParam(required = false) String contentType,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(placeService.recommendPlaces(request, contentType, page, limit));
    }

    @GetMapping("/detail/{id}")
    @Operation(
            summary = "명소의 상세 정보를 조회합니다",
            description = "congestion_level 0, 1, 2는 각각 여유, 보통, 혼잡을 나타냅니다",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PlaceDetailResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(@PathVariable Long id) {
        PlaceDetailResponse response;
        try {
            response = placeService.getPlaceDetail(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/like")
    @Operation(
            summary = "명소 좋아요",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "명소 좋아요 성공"
                    )
            }
    )
    public ResponseEntity<?> likePlace(
            @PathVariable Long id,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        memberLikePlaceService.likePlace(userInfo.getUserId(), id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/like")
    @Operation(
            summary = "명소 좋아요 취소",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "명소 좋아요 취소 성공"
                    )
            }
    )
    public ResponseEntity<?> unlikePlace(
            @PathVariable Long id,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        memberLikePlaceService.unlikePlace(userInfo.getUserId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
