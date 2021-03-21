package com.codegym.blog.demo.model.EntityIn;

import lombok.Data;

@Data
public class UserUpdateIn {
    private String firstName;

    private String lastName;

    private String email;

    private String avatarURL;
}
