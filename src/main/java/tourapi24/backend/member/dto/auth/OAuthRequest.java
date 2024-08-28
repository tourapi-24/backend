package tourapi24.backend.member.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthRequest {
    @NotNull
    private String accessToken;
}
