package com.example.student_management_backend.security;

import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import java.security.Key;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jwtExpirationInMs = 3600000; // 1 hour

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority()) // Thêm role vào payload
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
