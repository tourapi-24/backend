package tourapi24.backend.member.service.auth;

import tourapi24.backend.member.dto.auth.OAuthResponse;

public interface OAuthService {
    OAuthResponse auth(String accessToken);
}
