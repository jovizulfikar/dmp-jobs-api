package com.dmp.jobsapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dmp.jobsapi.dto.LoginResult;
import com.dmp.jobsapi.dto.PostLogin;
import com.dmp.jobsapi.dto.PostRegister;
import com.dmp.jobsapi.models.User;
import com.dmp.jobsapi.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> postRegister(@RequestBody PostRegister request) {
        User result = authService.register(request.toUser());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> postLogin(@RequestBody PostLogin request) {
        LoginResult result = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(result);
    }
}
