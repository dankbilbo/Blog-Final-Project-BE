package com.codegym.blog.demo.model.out;

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
    private boolean locked;

    public UserOut(Long id, String username, String firstName, String lastName, String email, String avatarURL, Set<UserRole> role, LocalDateTime createdAt, boolean locked) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatarURL = avatarURL;
        this.role = role;
        this.createdAt = createdAt;
        this.locked = locked;
    }
}
