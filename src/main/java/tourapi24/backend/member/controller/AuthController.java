package tourapi24.backend.member.controller;

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
public class AuthController {

    private final OAuthServiceFactory oAuthServiceFactory;

    @PostMapping("/oauth")
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
