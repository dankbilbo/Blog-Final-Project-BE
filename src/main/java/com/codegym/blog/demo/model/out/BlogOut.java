package com.codegym.blog.demo.model.out;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogOut {
    private Long id;
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURl;
    private Long userId;
    private String username;
    private LocalDateTime createdAt;
    private String tags;
    private Long categoryId;
    private boolean privacy;
    private Long views;
}
