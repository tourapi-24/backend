package tourapi24.backend.member.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.member.domain.Provider;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("provider")
    private Provider provider;
    @JsonProperty("access_token")
    private String accessToken;
}
