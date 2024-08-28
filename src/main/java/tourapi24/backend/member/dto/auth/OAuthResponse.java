package tourapi24.backend.member.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthResponse {
    private String type;
    private String message;
    private String token;
}
