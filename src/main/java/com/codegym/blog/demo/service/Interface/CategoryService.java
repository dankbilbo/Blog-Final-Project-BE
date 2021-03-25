package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.Entity.Category;

import java.util.List;

public interface CategoryService extends Service<Category> {
    List<Category> getTopFives();

    Long countNumberBlogsByCategory(Long id);
}
