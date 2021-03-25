package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Blog blog;

    public Comment(String content, LocalDateTime createdAt, User user, Blog blog) {
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.blog = blog;
    }
}
