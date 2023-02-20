package com.dmp.jobsapi.dto;

import com.dmp.jobsapi.models.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRegister {
    private String username;
    private String password;

    public User toUser() {
        return new User(username, password);
    }
}
