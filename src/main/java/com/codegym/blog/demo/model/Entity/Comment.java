package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @OneToOne
    private Comment repliedTo;

    @ManyToOne
    private Blog blog;
}
