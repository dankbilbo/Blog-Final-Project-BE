package com.codegym.blog.demo.model.EntityOut;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOut {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarURL;

    public UserOut(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
