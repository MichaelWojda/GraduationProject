package pl.mw.san.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenService.class);

    @Value("${app.jwtKey}")
    private String jwtKey;

    @Value("${app.expTime}")
    private int jwtExpTime;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJWTToken(Authentication authentication) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpTime);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .subject(String.valueOf(userPrincipal.getId()))
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claim = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claim.getSubject());

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            logger.error("Invalid JWT token: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty. " + ex.getMessage());
        }
        return false;
    }


}



