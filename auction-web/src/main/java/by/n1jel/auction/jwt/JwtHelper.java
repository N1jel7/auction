package by.n1jel.auction.jwt;

import by.n1jel.auction.config.JwtProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtHelper {

    private final JwtProperties jwtProperties;


    public JwtHelper(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Jws<Claims> extractClaims(String bearerToken) {
        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey())
                .build().parseClaimsJws(bearerToken);
    }

    public <T> T extractClaimBody(String bearerToken, Function<Claims, T> claimsResolver) {
        Jws<Claims> jwsClaims = extractClaims(bearerToken);
        log.info("Claims body: {}", jwsClaims.getBody());
        return claimsResolver.apply(jwsClaims.getBody());
    }

    public <T> T extractClaimHeader(String bearerToken, Function<JwsHeader, T> claimsResolver) {
        Jws<Claims> jwsClaims = extractClaims(bearerToken);
        return claimsResolver.apply(jwsClaims.getHeader());
    }

    public Date extractExpiry(String bearerToken) {
        return extractClaimBody(bearerToken, Claims::getExpiration);
    }

    public String extractUsername(String bearerToken) {
        return extractClaimBody(bearerToken, Claims::getSubject);
    }

    private Boolean isTokenExpired(String bearerToken) {
        log.info("Is before: {}", extractExpiry(bearerToken).before(new Date()));
        return extractExpiry(bearerToken).before(new Date());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        Date expiryDate = Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + jwtProperties.getLifetime()));
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(jwtProperties.getSecretKey()), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(hmacKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        log.info("Username from token: {}", username);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
