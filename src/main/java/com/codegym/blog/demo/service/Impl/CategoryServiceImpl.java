package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.repository.CategoryRepository;
import com.codegym.blog.demo.service.Interface.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getTopFives() {
        return categoryRepository.find5MostBlogsCategory();
    }

    @Override
    public Long countNumberBlogsByCategory(Long id) {
        return categoryRepository.countBlogsByCategoryId(id);
    }
}
