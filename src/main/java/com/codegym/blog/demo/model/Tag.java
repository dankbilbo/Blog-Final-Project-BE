package com.codegym.blog.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Tag extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private Set<Blog> blogs;
}
