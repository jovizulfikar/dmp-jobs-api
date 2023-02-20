package com.dmp.jobsapi.dto;

import com.dmp.jobsapi.models.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRegister {
    @NotBlank(message = "username must not be blank.")
    private String username;

    @Size(min = 6)
    private String password;

    public User toUser() {
        return new User(username, password);
    }
}
