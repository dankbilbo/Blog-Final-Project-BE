package com.codegym.blog.demo.model.EntityIn;

import lombok.Data;

@Data
public class UserChangeProfile {
    private String firstname;

    private String lastName;

    private String password;

    private String email;

    private String avatarURL;
}
