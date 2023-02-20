package com.dmp.jobsapi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmp.jobsapi.config.JwtProperties;
import com.dmp.jobsapi.dto.LoginResult;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    @Autowired
    JwtProperties jwtProperties;

    public LoginResult generateToken(JwtPayload jwtPayload) {
        String token = Jwts.builder()
            .setSubject(jwtPayload.username())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
            .compact();

        return new LoginResult(token, jwtProperties.getExpire());
    }

    public String getUserUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtProperties.getSecret())
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }
}
