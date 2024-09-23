package tourapi24.backend.travellog.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.travellog.dto.TravelLogCreateRequest;
import tourapi24.backend.travellog.dto.TravelLogCreateResponse;
import tourapi24.backend.travellog.dto.TravelLogResponse;
import tourapi24.backend.travellog.service.TravelLogService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travellog")
@Tag(name = "Travel Log")
public class TravelLogController {

    private final TravelLogService travelLogService;

    @PostMapping
    @Operation(
            summary = "여행기 작성",
            description = "여행기를 생성하고, 생성된 여행기의 ID를 반환합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TravelLogCreateResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<TravelLogCreateResponse> createTravelLog(
            @RequestBody TravelLogCreateRequest request,
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {

        return new ResponseEntity<>(travelLogService.createTravelLog(userInfo, request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "여행기 조회",
            description = "여행기 ID로 여행기를 조회합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TravelLogResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<TravelLogResponse> getTravelLog(@PathVariable Long id) {
        TravelLogResponse travelLog = travelLogService.getTravelLog(id);
        return ResponseEntity.ok(travelLog);
    }
}
