package com.codegym.blog.demo.model.out;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentOut {
    private Long id;
    private String content;
    private Long blogId;
    private Long userId;
    private String username;
    private LocalDateTime commentedAt;
}
