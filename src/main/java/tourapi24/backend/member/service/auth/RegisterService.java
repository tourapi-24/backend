package tourapi24.backend.member.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.dto.auth.LoginResponse;
import tourapi24.backend.member.dto.auth.RegisterRequest;
import tourapi24.backend.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterService {

    private final OAuthClient oAuthClient;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        System.out.println("request = " + request);
        UserInfo userInfo = oAuthClient.fetchUserInfo(
                request.getProvider(),
                request.getAccessToken()
        );
        System.out.println("userInfo = " + userInfo);
        Member member = memberRepository.save(userInfo.toMember());

        return LoginResponse.builder()
                .token(jwtService.generateToken(member))
                .build();
    }
}
