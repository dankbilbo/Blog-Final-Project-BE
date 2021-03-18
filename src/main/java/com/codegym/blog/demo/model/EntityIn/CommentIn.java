package com.codegym.blog.demo.model.EntityIn;

import lombok.Data;

@Data
public class CommentIn {
    private String content;
    private Long userId;
    private Long blogId;
    private Long commentId;
}
