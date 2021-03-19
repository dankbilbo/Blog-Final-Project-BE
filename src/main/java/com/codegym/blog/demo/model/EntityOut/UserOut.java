package com.codegym.blog.demo.model.EntityOut;

import com.codegym.blog.demo.model.Entity.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserOut {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarURL;
    private Set<UserRole> role;
    private LocalDateTime createdAt;

    public UserOut(Long id,String username, String email, Set<UserRole> role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }
}
