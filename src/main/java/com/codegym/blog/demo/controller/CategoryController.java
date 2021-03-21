package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.CategoryActionService;
import com.codegym.blog.demo.service.Impl.ActionCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    public CategoryActionService categoryService;

    @GetMapping
    public ResponseEntity<SystemResponse<List<Category>>> getAllCategory() {
        return categoryService.categoryList();
    }

    @PostMapping("/create")
    public ResponseEntity<SystemResponse<Category>> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<Category>> findById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Category>> updateById(@PathVariable("id") Long id, @RequestBody Category category) {
        return categoryService.updateById(id, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Category>> deleteById(@PathVariable("id") Long id) {
        return categoryService.deleteById(id);
    }
}
