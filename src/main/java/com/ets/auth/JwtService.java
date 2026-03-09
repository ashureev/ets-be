package com.ets.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET = "CHANGE_THIS_TO_A_LONG_RANDOM_SECRET_KEY";

    public String createToken(String subject, String role) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(Map.of("role", role))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }
}