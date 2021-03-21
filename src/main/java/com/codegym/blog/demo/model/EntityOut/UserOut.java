package com.codegym.blog.demo.model.EntityOut;

import com.codegym.blog.demo.model.Entity.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserOut {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarURL;
    private Set<UserRole> role;
    private LocalDateTime createdAt;

    public UserOut(Long id, String username, String firstName, String lastName, String email, String avatarURL, Set<UserRole> role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarURL = avatarURL;
        this.role = role;
        this.createdAt = createdAt;
    }
}
