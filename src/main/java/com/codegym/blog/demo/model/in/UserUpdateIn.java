package com.codegym.blog.demo.model.in;

import lombok.Data;

@Data
public class UserUpdateIn {
    private String firstName;

    private String lastName;

    private String email;

    private String avatarURL;
}
