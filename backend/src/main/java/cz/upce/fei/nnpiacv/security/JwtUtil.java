package cz.upce.fei.nnpiacv.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "my-super-secret-key-my-super-secret-key"; // Min. 32 znaků
    private static final long EXPIRATION_TIME = 86400000; // 1 den

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private SecretKey getSigningKey() {
        return secretKey;
    }


    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey()) // Nový způsob podepisování
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Nová metoda pro validaci v JJWT 0.12.x
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(getSigningKey()) // Nová metoda pro validaci
                    .build()
                    .parseSignedClaims(token);

            return !claimsJws.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
