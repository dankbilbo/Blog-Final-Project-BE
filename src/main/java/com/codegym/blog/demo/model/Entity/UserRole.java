package com.codegym.blog.demo.model.Entity;

import javax.persistence.Enumerated;

public class UserRole {
    private Long id;

    @Enumerated
    private RoleName roleName;
}
