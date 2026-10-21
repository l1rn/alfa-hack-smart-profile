package org.kirpich.smart_profile.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.kirpich.smart_profile.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.token}")
    private String jwtSigningKey;

    @Getter
    @Value("${jwt.access-exp}")
    private long accessExp;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExp))
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        }
        catch(ExpiredJwtException e){
            log.info("Token expired");
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = Base64.getDecoder()
                .decode(jwtSigningKey.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
