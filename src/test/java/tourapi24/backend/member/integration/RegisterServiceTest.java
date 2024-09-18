package tourapi24.backend.member.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tourapi24.backend.gaongi.domain.Gaongi;
import tourapi24.backend.gaongi.repository.GaongiRepository;
import tourapi24.backend.member.domain.AgeRange;
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
                .birthday("0230") // 실제 OAUth Privider에는 저장될 수 없는 생일로 설정해 Request 기반으로 저장하고 있는지 확인
                .ageRange(AgeRange.SIXTY)
                .gender(Gender.FEMALE)
                .build();

        // when
        registerService.register(request);

        // then
        Member member = memberRepository.findOneBySocialId(realSocialId).orElseThrow();
        assertEquals(request.getUsername(), member.getUsername());
        assertEquals(request.getBirthday(), member.getBirthday());
        assertEquals(request.getAgeRange(), member.getAgeRange());
        assertEquals(request.getGender(), member.getGender());

        Gaongi gaongi = gaongiRepository.findByMemberId(member.getId());
        assertEquals(1, gaongi.getLevel());
        assertEquals(0, gaongi.getExp());
    }
}
