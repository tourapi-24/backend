package tourapi24.backend.member.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.LoginRequest;
import tourapi24.backend.member.dto.auth.LoginResponse;
import tourapi24.backend.member.dto.external.KakaoUserInfoResponse;
import tourapi24.backend.member.dto.external.UserInfoResponse;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.member.service.auth.JwtService;
import tourapi24.backend.member.service.auth.OAuthClient;
import tourapi24.backend.member.service.auth.OAuthService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OAuthServiceTest {

    @Mock
    private OAuthClient oAuthClient;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OAuthService OAuthService;

    // Mock Data
    private LoginRequest mockKakaoLoginRequest;
    private UserInfoResponse mockKakaoUserInfoResponse;
    private Member mockKakaoMember;

    @BeforeEach
    void setUp() {
        mockKakaoLoginRequest = LoginRequest.builder()
                .provider(Provider.KAKAO)
                .accessToken("mock_access_token")
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
        mockKakaoMember = Member.builder().build();
    }

    @Test
    void 로그인_성공_카카오() {
        // given
        when(oAuthClient.fetchUserInfo(any(), any()))
                .thenReturn(mockKakaoUserInfoResponse.toUserInfo());
        when(memberRepository.findOneBySocialId(anyString()))
                .thenReturn(Optional.of(mockKakaoMember));
        when(jwtService.generateToken(any()))
                .thenReturn("mock_jwt");

        // when
        LoginResponse response = OAuthService.auth(mockKakaoLoginRequest);

        // then
        assertNotNull(response);
        assertEquals("mock_jwt", response.getToken());
    }

    @Test
    void 로그인_회원가입_전환_카카오() {
        // given
        when(oAuthClient.fetchUserInfo(any(), any()))
                .thenReturn(mockKakaoUserInfoResponse.toUserInfo());
        when(memberRepository.findOneBySocialId(anyString()))
                .thenReturn(Optional.empty());
        when(jwtService.generateTempToken(any())).
                thenReturn("mock_temp_jwt");

        // when
        LoginResponse response = OAuthService.auth(mockKakaoLoginRequest);

        // then
        assertNotNull(response);
        assertEquals("mock_temp_jwt", response.getToken());
    }
}
