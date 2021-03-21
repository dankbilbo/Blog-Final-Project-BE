package com.codegym.blog.demo.model.in;

import lombok.Data;

@Data
public class BlogAddIn {
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURL;
    private Long idCategory;
    private String tags;
    private boolean privacy;
}
