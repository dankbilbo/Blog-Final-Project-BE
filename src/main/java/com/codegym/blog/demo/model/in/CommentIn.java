package com.codegym.blog.demo.model.in;

import lombok.Data;

@Data
public class CommentIn {
    private String content;
    private Long userId;
    private Long blogId;
    private Long commentId;
}
