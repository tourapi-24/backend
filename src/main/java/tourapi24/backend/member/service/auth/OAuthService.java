package tourapi24.backend.member.service.auth;

import tourapi24.backend.member.dto.auth.OAuthResponse;
import tourapi24.backend.member.dto.auth.RegisterRequest;

public interface OAuthService {
    OAuthResponse auth(String accessToken);

    OAuthResponse register(String accessToken, RegisterRequest request);
}
