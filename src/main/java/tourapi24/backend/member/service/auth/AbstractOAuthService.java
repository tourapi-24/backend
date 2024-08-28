package tourapi24.backend.member.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.OAuthResponse;
import tourapi24.backend.member.repository.MemberRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class AbstractOAuthService implements OAuthService {

    protected final RestTemplate restTemplate;
    protected final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuthResponse auth(String accessToken) {
        UserInfo userInfo = retrieveUserInfoFromProvider(accessToken);
        if (validateDuplicateMember(userInfo.getSocialId())) {
            return handleExistingMember(userInfo);
        } else {
            return handleNewMember(userInfo);
        }
    }

    protected abstract UserInfo retrieveUserInfoFromProvider(String accessToken);

    protected abstract Provider getProvider();

    protected boolean validateDuplicateMember(String socialId) {
        return memberRepository.findOneBySocialId(socialId).isPresent();
    }

    private OAuthResponse handleExistingMember(UserInfo userInfo) {
        Member member = memberRepository.findOneBySocialId(userInfo.getSocialId()).orElseThrow();
        return OAuthResponse.builder()
                .type("SIGN_IN")
                .message("success")
                .token(generateToken(member))
                .build();
    }

    private OAuthResponse handleNewMember(UserInfo userInfo) {
        Member member = signUp(userInfo);
        return OAuthResponse.builder()
                .type("SIGN_UP")
                .message("success")
                .token(generateToken(member))
                .build();
    }

    private Member signUp(UserInfo userInfo) {
        Member member = Member.builder()
                .socialId(userInfo.getSocialId())
                .email(userInfo.getEmail())
                .username(userInfo.getUsername())
                .provider(getProvider())
                .gender(userInfo.getGender())
                .profileImage(userInfo.getProfileImage())
                .birthday(userInfo.getBirthday())
                .ageRange(userInfo.getAgeRange())
                .build();
        return memberRepository.save(member);
    }

    protected String generateToken(Member member) {
        return "TOKEN_" + member.getId();
    }
}
