package com.codegym.blog.demo.model.Entity;

import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    private String firstname;

    private String lastName;


    private String username;

    private String password;

    private String email;

    private String avatarURL;

    private LocalDateTime createdAt;

    @Enumerated
    private UserRole userRole;


    private boolean enabled = false;

    private boolean locked = false;
    private boolean expired = false;

    public User(String firstname, String lastName, String username, String password, String email, String avatarURL, LocalDateTime createdAt, UserRole userRole, boolean enabled, boolean locked, boolean expired) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatarURL = avatarURL;
        this.createdAt = createdAt;
        this.userRole = userRole;
        this.enabled = enabled;
        this.locked = locked;
        this.expired = expired;
    }

    public User(String username, String password, String email, LocalDateTime createdAt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    // security

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
//        return Collections.singletonList(authority);
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return !expired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !locked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }

}
