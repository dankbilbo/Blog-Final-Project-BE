package com.codegym.blog.demo.model.EntityIn;

import com.codegym.blog.demo.model.Entity.Category;
import lombok.Data;

@Data
public class BlogAddIn {
    private String title;
    private String content;
    private String shortDescription;
    private String previewImageURL;
    private Long idCategory;
}
