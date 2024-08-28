package tourapi24.backend.member.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthServiceFactory {

    private final KakaoAuthService kakaoAuthService;
    private final NaverAuthService naverAuthService;

    public OAuthService getOAuthService(String provider) {
        return switch (provider.toLowerCase()) {
            case "kakao" -> kakaoAuthService;
            case "naver" -> naverAuthService;
            default -> throw new IllegalArgumentException("Unsupported OAuth provider: " + provider);
        };
    }
}
