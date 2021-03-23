package com.codegym.blog.demo.model.out;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentOut {
    private String content;
    private Long blogId;
    private Long userId;
    private Long commentId;
    private LocalDateTime commentedAt;
}
