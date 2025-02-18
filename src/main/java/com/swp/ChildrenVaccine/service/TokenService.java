package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.security.Key;
@Service
public class TokenService {
    private final String SECRET_KEY = "your-secret-key-should-be-long-and-secure";  // Thay bằng khóa bí mật thực tế
    private final long EXPIRATION_TIME = 86400000; // 1 ngày

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
