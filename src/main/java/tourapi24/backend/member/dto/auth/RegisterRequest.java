package tourapi24.backend.member.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Provider;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @JsonProperty("access_token")
    private String accessToken;
    private Provider provider;

    @Size(min = 2, max = 6)
    private String username;
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$") // MMDD TODO: validate birthday
    private String birthday;
    @JsonProperty("age_range")
    private AgeRange ageRange;
    private Gender gender;
}
