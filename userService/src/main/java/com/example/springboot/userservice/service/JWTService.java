package com.example.springboot.userservice.service;


import com.example.springboot.userservice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequestMapping
@Slf4j
public class JWTService {

    @Value("${spring.secretKey}")
    private String SECRET_KEY;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getUserId().toString())
                .claim("email", user.getEmail())
                .signWith(getSecretKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 1000 * 100))
                .compact();
    }


    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.getSubject());
    }
}
