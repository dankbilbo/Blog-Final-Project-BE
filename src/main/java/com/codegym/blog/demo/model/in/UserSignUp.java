package com.codegym.blog.demo.model.in;

import lombok.Data;

@Data
public class UserSignUp {
    private String username;
    private String password;
    private String email;
}
