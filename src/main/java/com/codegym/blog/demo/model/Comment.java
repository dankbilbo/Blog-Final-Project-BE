package com.codegym.blog.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String content;

    @ManyToOne
    private User user;

    @OneToOne
    private Comment repliedTo;

    @ManyToOne
    private Blog blog;
}
