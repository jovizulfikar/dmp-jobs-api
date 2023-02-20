package com.dmp.jobsapi.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dmp.jobsapi.config.JwtProperties;
import com.dmp.jobsapi.dto.LoginResult;
import com.dmp.jobsapi.models.User;
import com.dmp.jobsapi.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProperties jwtProperties;

    private BCryptPasswordEncoder bcrypt;

    public AuthService() {
        this.bcrypt = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());

        if (existUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already in use.");
        }
        
        user.setPassword(bcrypt.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        registeredUser.setPassword(null);

        return registeredUser;
    }

    public LoginResult login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password."));

        if(!bcrypt.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password.");
        }

        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire() * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
            .compact();
        
        return new LoginResult(token, jwtProperties.getExpire());
    }
}
