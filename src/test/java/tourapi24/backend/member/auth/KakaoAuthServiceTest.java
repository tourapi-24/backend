package tourapi24.backend.member.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.OAuthResponse;
import tourapi24.backend.member.repository.MemberRepository;
import tourapi24.backend.member.service.auth.JwtService;
import tourapi24.backend.member.service.auth.KakaoAuthService;
import tourapi24.backend.member.service.auth.UserInfo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KakaoAuthServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private KakaoAuthService kakaoAuthService;

    // Mock Data
    private KakaoAuthService.KakaoUserInfo mockKakaoUserInfo;
    private UserInfo mockUserInfo;
    private Member mockMember;
    private String mockAccessToken;

    @BeforeEach
    void setUp() {
        mockKakaoUserInfo = KakaoAuthService.KakaoUserInfo.builder()
                .id(3679105054L)
                .kakaoAccount(KakaoAuthService.KakaoUserInfo.KakaoAccount.builder()
                        .email("ilcm96@gmail.com")
                        .profile(KakaoAuthService.KakaoUserInfo.KakaoAccount.Profile.builder()
                                .nickname("윤성민")
                                .profileImageUrl("http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
                                .isDefaultImage(true)
                                .isDefaultNickname(false)
                                .build())
                        .build())
                .build();
        mockUserInfo = mockKakaoUserInfo.toUserInfo();
        mockMember = Member.builder()
                .id(1L)
                .socialId(mockUserInfo.getSocialId())
                .email(mockUserInfo.getEmail())
                .username(mockUserInfo.getUsername())
                .provider(Provider.KAKAO)
                .gender(mockUserInfo.getGender())
                .profileImage(mockUserInfo.getProfileImage())
                .birthday(mockUserInfo.getBirthday())
                .ageRange(mockUserInfo.getAgeRange())
                .build();
        mockAccessToken = "mock_access_token";
    }

    @Test
    void 회원가입() {
        //given
        when(restTemplate.exchange(anyString(), any(), any(), eq(KakaoAuthService.KakaoUserInfo.class)))
                .thenReturn(ResponseEntity.ok(mockKakaoUserInfo));
        when(memberRepository.findOneBySocialId(anyString())).
                thenReturn(Optional.empty());
        when(memberRepository.save(any()))
                .thenReturn(mockMember);
        when(jwtService.generateToken(anyLong(), anyString()))
                .thenReturn("mock_jwt");

        // when
        OAuthResponse response = kakaoAuthService.auth(mockAccessToken);

        // then
        assertNotNull(response);
        assertEquals("SIGN_UP", response.getType());
        assertEquals("success", response.getMessage());
        assertEquals("mock_jwt", response.getToken());

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void 로그인() {
        // given
        when(restTemplate.exchange(anyString(), any(), any(), eq(KakaoAuthService.KakaoUserInfo.class)))
                .thenReturn(ResponseEntity.ok(mockKakaoUserInfo));
        when(memberRepository.findOneBySocialId(anyString()))
                .thenReturn(Optional.of(mockMember));
        when(jwtService.generateToken(anyLong(), anyString()))
                .thenReturn("mock_jwt");

        // when
        OAuthResponse response = kakaoAuthService.auth(mockAccessToken);

        // then
        assertNotNull(response);
        assertEquals("SIGN_IN", response.getType());
        assertEquals("success", response.getMessage());
        assertEquals("mock_jwt", response.getToken());

        verify(memberRepository, never()).save(any(Member.class));

    }
}
