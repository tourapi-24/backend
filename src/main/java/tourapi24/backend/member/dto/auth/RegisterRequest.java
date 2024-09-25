package tourapi24.backend.member.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Provider;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("provider")
    private Provider provider;

    @JsonProperty("username")
    @Size(min = 2, max = 6)
    private String username;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("is_local")
    private Boolean isLocal;
}
