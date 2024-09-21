package tourapi24.backend.member.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tourapi24.backend.gaongi.service.GaongiService;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.LoginResponse;
import tourapi24.backend.member.dto.auth.RegisterRequest;
import tourapi24.backend.member.dto.external.KakaoUserInfoResponse;
import tourapi24.backend.member.dto.external.UserInfoResponse;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.member.service.auth.JwtService;
import tourapi24.backend.member.service.auth.OAuthClient;
import tourapi24.backend.member.service.auth.RegisterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private GaongiService gaongiService;

    @InjectMocks
    private RegisterService registerService;

    // Mock Data
    private RegisterRequest mockKakaoRegisterRequest;
    private UserInfoResponse mockKakaoUserInfoResponse;
    private Member mockKakaoMember;

    @BeforeEach
    void setUp() {
        mockKakaoRegisterRequest = RegisterRequest.builder()
                .accessToken("mock_access_token")
                .provider(Provider.KAKAO)
                .username("윤성민")
                .birthday("0903")
                .ageRange(AgeRange.TWENTY)
                .gender(Gender.MALE)
                .isLocal(true)
                .build();

        mockKakaoUserInfoResponse = KakaoUserInfoResponse.builder()
                .id(123456789L)
                .kakaoAccount(KakaoUserInfoResponse.KakaoAccount.builder()
                        .email("ilcm96@gmail.com")
                        .profile(KakaoUserInfoResponse.KakaoAccount.Profile.builder()
                                .nickname("윤성민")
                                .profileImageUrl("http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
                                .isDefaultImage(true)
                                .isDefaultNickname(false)
                                .build())
                        .build())
                .build();
        mockKakaoMember = mockKakaoUserInfoResponse.toUserInfo().toMember(mockKakaoRegisterRequest);
    }

    @Test
    void 회원등록_카카오() {
        // given
        when(oAuthClient.fetchUserInfo(any(), any()))
                .thenReturn(mockKakaoUserInfoResponse.toUserInfo());
        when(memberRepository.save(any()))
                .thenReturn(mockKakaoMember);
        when(jwtService.generateToken(any()))
                .thenReturn("mock_jwt");
        doNothing().when(gaongiService).create(any());

        // then
        LoginResponse response = registerService.register(mockKakaoRegisterRequest);

        // then
        assertNotNull(response);
        assertEquals("mock_jwt", response.getToken());
    }
}
