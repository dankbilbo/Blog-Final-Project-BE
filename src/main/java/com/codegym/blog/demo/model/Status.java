package com.codegym.blog.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Status extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime timeLiked;

    private Emo emo;

    @ManyToOne
    private Blog blog;


}
