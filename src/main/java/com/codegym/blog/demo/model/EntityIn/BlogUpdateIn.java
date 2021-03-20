package com.codegym.blog.demo.model.EntityIn;

import lombok.Data;

@Data
public class BlogUpdateIn {
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURL;
    private boolean privacy;
}
