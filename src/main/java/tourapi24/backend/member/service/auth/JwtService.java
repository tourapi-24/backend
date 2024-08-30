package tourapi24.backend.member.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourapi24.backend.member.domain.AgeRange;
import tourapi24.backend.member.domain.Gender;

import java.security.KeyPair;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyPair keyPair;

    @Value("${jwt.expiration}")
    private long expiration;

    public static Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(Long.toString(userId))
                .claim("username", username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    public String generateTempToken(String userId, String username, String birthday, AgeRange ageRange, Gender gender) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 300000); // 5 minutes

        return Jwts.builder()
                .subject(userId)
                .claim("temp", true)
                .claim("username", username)
                .claim("birthday", birthday)
                .claim("ageRange", ageRange)
                .claim("gender", gender)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(keyPair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
