package tourapi24.backend.member.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourapi24.backend.member.domain.Member;

import java.security.KeyPair;
import java.time.Clock;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyPair keyPair;
    private final Clock clock;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.temporary-expiration}")
    private long temporaryExpiration;

//    public static Claims getClaimFromToken(String token) {
//        return Jwts.parser()
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }

    public String generateToken(Member member) {
        Date now = Date.from(clock.instant());
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(Long.toString(member.getId())) // <-> 임시 JWT의 sub는 socialId
                .claim("username", member.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    public String generateTempToken(UserInfo userInfo) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + temporaryExpiration);

        return Jwts.builder()
                .subject(userInfo.getSocialId()) // <-> 정식 JWT의 sub는 id
                .claim("temp", true)
                .claim("prvider", userInfo.getProvider())
                .claim("username", userInfo.getUsername())
                .claim("birthday", userInfo.getBirthday())
                .claim("ageRange", userInfo.getAgeRange())
                .claim("gender", userInfo.getGender())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .clock(() -> Date.from(clock.instant()))
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
