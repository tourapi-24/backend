package tourapi24.backend.gaongi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.annotation.CurrentUser;
import tourapi24.backend.annotation.CurrentUserInfo;
import tourapi24.backend.gaongi.dto.GaongiResponse;
import tourapi24.backend.gaongi.service.GaongiService;

@RestController
@RequestMapping("/gaongi")
@RequiredArgsConstructor
@Tag(name = "Gaongi")
public class GaongiController {

    private final GaongiService gaongiService;

    @GetMapping
    @Operation(
            summary = "가옹이 정보를 조회합니다",
            description = "가옹이 레벨은 1~5까지 아기가옹이부터 황금가옹이까지 입니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "가옹이 정보 조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = GaongiResponse.class)
                            )
                    ),
            }
    )
    public ResponseEntity<GaongiResponse> getGaongi(
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        return new ResponseEntity<>(
                gaongiService.getGaongi(userInfo.getUserId()),
                HttpStatus.OK
        );
    }

    @PostMapping("/exp/increase")
    @Operation(
            summary = "경험치를 1 증가시킵니다",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "경험치 증가 성공",
                            content = @Content(
                                    schema = @Schema(implementation = GaongiResponse.class)
                            )
                    ),
            }
    )
    public ResponseEntity<GaongiResponse> increaseExp(
            @Parameter(hidden = true) @CurrentUser CurrentUserInfo userInfo
    ) {
        return new ResponseEntity<>(
                gaongiService.increaseExp(userInfo.getUserId()),
                HttpStatus.CREATED
        );
    }
}
