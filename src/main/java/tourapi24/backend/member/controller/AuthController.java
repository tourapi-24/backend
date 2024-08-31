package tourapi24.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourapi24.backend.member.dto.auth.LoginRequest;
import tourapi24.backend.member.dto.auth.LoginResponse;
import tourapi24.backend.member.dto.auth.RegisterRequest;
import tourapi24.backend.member.service.auth.OAuthService;
import tourapi24.backend.member.service.auth.RegisterService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final OAuthService OAuthService;
    private final RegisterService registerService;

    @PostMapping("/oauth")
    @Operation(
            summary = "OAuth의 access token을 이용해 사용자를 인증하고 JWT을 발급합니다",
            description = "이미 가입된 회원의 경우 정식 JWT을 발급하고, 신규 회원의 경우 임시 JWT을 발급합니다",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", content = @Content)
            }
    )
    public ResponseEntity<LoginResponse> auth(@Valid @RequestBody LoginRequest request) {
        try {
            return new ResponseEntity<>(OAuthService.auth(request), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("{} {}", e.getClass(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    @Operation(
            summary = "추가 회원 정보를 통해 신규 회원을 가입시키고, 정식 JWT을 발급합니다",
            description = "이 단계를 통과해야 실제로 회원이 DB에 저장됩니다",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", content = @Content),
                    @ApiResponse(responseCode = "401", content = @Content)
            }
    )
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            return new ResponseEntity<>(registerService.register(request), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("{} {}", e.getClass(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
