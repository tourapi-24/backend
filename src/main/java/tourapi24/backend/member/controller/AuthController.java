package tourapi24.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourapi24.backend.member.dto.auth.OAuthRequest;
import tourapi24.backend.member.dto.auth.OAuthResponse;
import tourapi24.backend.member.service.auth.OAuthService;
import tourapi24.backend.member.service.auth.OAuthServiceFactory;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final OAuthServiceFactory oAuthServiceFactory;

    @PostMapping("/oauth")
    @Operation(
            summary = "OAuth의 access token을 이용해 사용자를 인증하고 JWT 토큰을 발급합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OAuthResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", content = @Content)
            }
    )
    @Parameter(name = "provider", required = true, schema = @Schema(allowableValues = {"kakao", "naver"}))
    public ResponseEntity<OAuthResponse> auth(
            @RequestParam String provider,
            @Valid @RequestBody OAuthRequest request
    ) {
        try {
            OAuthService oAuthService = oAuthServiceFactory.getOAuthService(provider);
            return new ResponseEntity<>(oAuthService.auth(request.getAccessToken()), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("{} {}", e.getClass(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
