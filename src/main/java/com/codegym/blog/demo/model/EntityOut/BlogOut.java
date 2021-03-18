package com.codegym.blog.demo.model.EntityOut;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogOut extends BaseEntityOut{
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURl;
    private Long userId;
}
