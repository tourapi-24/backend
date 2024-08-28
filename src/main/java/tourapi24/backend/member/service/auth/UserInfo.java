package tourapi24.backend.member.service.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tourapi24.backend.member.domain.Gender;

@Getter
@Builder
@ToString
public class UserInfo {
    private String socialId;
    private String email;
    private String username;
    private Gender gender;
    private String profileImage;
    private String birthday;
    private Integer ageRange;
}
