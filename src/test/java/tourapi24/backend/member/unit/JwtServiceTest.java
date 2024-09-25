package tourapi24.backend.member.unit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import tourapi24.backend.member.domain.Gender;
import tourapi24.backend.member.domain.Member;
import tourapi24.backend.member.domain.Provider;
import tourapi24.backend.member.dto.auth.RegisterRequest;
import tourapi24.backend.member.service.auth.JwtService;
import tourapi24.backend.member.service.auth.UserInfo;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Clock;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private KeyPair keyPair;

    @Mock
    private Clock clock;

    @InjectMocks
    private JwtService jwtService;

    // Mock Data
    private Member mockMember;
    private UserInfo mockUserInfo;
    private RegisterRequest mockRegisterRequest;

    @BeforeEach
    void setUp() throws Exception {
        mockUserInfo = UserInfo.builder()
                .socialId("12345")
                .email("test@test.com")
                .username("testuser")
                .provider(Provider.KAKAO)
                .gender(Gender.MALE)
                .build();

        mockRegisterRequest = RegisterRequest.builder()
                .username("testuser")
                .gender(Gender.MALE)
                .isLocal(true)
                .build();

        mockMember = mockUserInfo.toMember(mockRegisterRequest);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair generatedKeyPair = keyPairGenerator.generateKeyPair();

        lenient().when(keyPair.getPrivate()).thenReturn(generatedKeyPair.getPrivate());
        lenient().when(keyPair.getPublic()).thenReturn(generatedKeyPair.getPublic());

        lenient().when(clock.instant()).thenReturn(new Date().toInstant());

        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L); // 1 hour
        ReflectionTestUtils.setField(jwtService, "temporaryExpiration", 300000L); // 5 minutes
        ReflectionTestUtils.setField(mockMember, "id", 1L); // DB에 저장되기 전이라 id가 null임
    }

    @Test
    void JWT_생성() {
        // when
        String token = jwtService.generateToken(mockMember);

        // then
        assertNotNull(token);
        Claims claims = jwtService.validateToken(token);
        assertEquals("1", claims.getSubject());
        assertEquals("testuser", claims.get("username"));
    }

    @Test
    void 임시_JWT_생성() {
        // when
        String token = jwtService.generateTempToken(mockUserInfo);

        // then
        assertNotNull(token);

        Claims claims = jwtService.validateToken(token);
        assertEquals("12345", claims.getSubject());
        assertTrue((Boolean) claims.get("temp"));
        assertEquals("KAKAO", claims.get("prvider"));
        assertEquals("testuser", claims.get("username"));
        assertEquals("MALE", claims.get("gender"));

        Date expirationDate = claims.getExpiration();
        Date issuedAt = claims.getIssuedAt();

        long diff = expirationDate.getTime() - issuedAt.getTime();
        assertEquals(300000L, diff); // 5 minutes difference
    }

    @Test
    void 토큰검증_성공() {
        // given
        String token = jwtService.generateToken(mockMember);

        // when
        Claims claims = jwtService.validateToken(token);

        // then
        assertNotNull(claims);
        assertEquals("1", claims.getSubject());
        assertEquals("testuser", claims.get("username"));
    }

    @Test
    void 토큰검증_실패_expire() throws InterruptedException {
        // given
        Date now = Date.from(clock.instant());
        Date expiryDate = new Date(now.getTime() + 1);

        String expiredToken = Jwts.builder()
                .subject(Long.toString(mockMember.getId()))
                .claim("username", mockMember.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();

        // when
        when(clock.instant())
                .thenReturn(new Date(expiryDate.getTime()).toInstant().plusMillis(10000));

        // then
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(expiredToken));
    }

    @Test
    void 토큰검증_실패_SIG() throws Exception {
        // given
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 10000);

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair invalidKeyPair = keyPairGenerator.generateKeyPair();

        String invalidToken = Jwts.builder()
                .subject(Long.toString(mockMember.getId()))
                .claim("username", mockMember.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(invalidKeyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();

        // when then
        assertThrows(Exception.class, () -> jwtService.validateToken(invalidToken));
    }
}
