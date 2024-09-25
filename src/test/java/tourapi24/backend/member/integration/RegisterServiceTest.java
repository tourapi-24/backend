package tourapi24.backend.member.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.RegisterRequest;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.member.service.auth.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GaongiRepository gaongiRepository;

    @Value("${oauth.access-token}")
    private String realAccessToken;

    @Value("${oauth.social-id}")
    private String realSocialId;

    @Test
    void Provider_DB_연동_회원가입() { // application.yml에 실제 토큰 설정 후 실행
        // given
        RegisterRequest request = RegisterRequest.builder()
                .accessToken(realAccessToken)
                .provider(Provider.KAKAO)
                .username("username_from_request")
                .gender(Gender.FEMALE)
                .isLocal(true)
                .build();

        // when
        registerService.register(request);

        // then
        Member member = memberRepository.findOneBySocialId(realSocialId).orElseThrow();
        assertEquals(request.getUsername(), member.getUsername());
        assertEquals(request.getGender(), member.getGender());
        assertEquals(request.getIsLocal(), member.getIsLocal());

        Gaongi gaongi = gaongiRepository.findByMemberId(member.getId());
        assertEquals(1, gaongi.getLevel());
        assertEquals(0, gaongi.getExp());
    }
}
