package com.codegym.blog.demo.model.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    private LocalDateTime timeLiked;

    private boolean isLiked;

    @ManyToOne
    private Blog blog;

    public Status(LocalDateTime createdAt, User user, LocalDateTime timeLiked, boolean liked, Blog blog) {
        this.createdAt = createdAt;
        this.user = user;
        this.timeLiked = timeLiked;
        this.isLiked = liked;
        this.blog = blog;
    }
}
