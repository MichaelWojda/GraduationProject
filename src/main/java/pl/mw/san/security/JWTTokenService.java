package pl.mw.san.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JWTTokenService.class);

    @Value("${app.jwtKey}")
    private String jwtKey;

    @Value("${app.expTime}")
    private int jwtExpTime;


    public String generateJWTToken(Authentication authentication) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpTime);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claim = Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claim.getSubject());

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token "+ ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token "+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty. " + ex.getMessage());
        }
        return false;
    }


}



