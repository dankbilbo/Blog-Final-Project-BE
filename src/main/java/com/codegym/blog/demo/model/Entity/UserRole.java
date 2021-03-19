package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.nio.file.FileStore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String roleName;

    public UserRole(String roleName) {
        this.roleName = roleName;
    }
}
