package com.codegym.blog.demo.model.Entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String avatarURL;

    private LocalDateTime createdAt;

    private boolean locked = false;

    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles"
            ,joinColumns = @JoinColumn(name="user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> role = new HashSet<>();


    public User(String username, String password, String email, LocalDateTime createdAt,Set roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.role = roles;
    }

    public User(String username) {
        this.username = username;
    }
}
