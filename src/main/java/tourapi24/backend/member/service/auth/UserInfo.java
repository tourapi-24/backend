package tourapi24.backend.member.service.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.RegisterRequest;

@Getter
@Builder
@ToString
public class UserInfo {
    // Long id -> auto-generated
    private String socialId;
    private String email;
    private String username;
    private Provider provider;
    private Gender gender;
    private AgeRange ageRange;
    private String profileImage;
    private String birthday;
    // String bio -> set on my page

    public Member toMember(RegisterRequest request) {
        return Member.builder()
                .socialId(socialId)
                .email(email)
                .username(request.getUsername())
                .provider(provider)
                .gender(request.getGender())
                .ageRange(request.getAgeRange())
                .profileImage(profileImage)
                .birthday(request.getBirthday())
                .build();
    }
}
