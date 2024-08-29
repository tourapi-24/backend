package tourapi24.backend.member.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthRequest {
    @NotNull
    @JsonProperty("access_token")
    private String accessToken;
}
