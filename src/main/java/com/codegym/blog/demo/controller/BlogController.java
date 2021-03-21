package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.in.BlogAddIn;
import com.codegym.blog.demo.model.in.BlogUpdateIn;
import com.codegym.blog.demo.model.in.SearchBlogIn;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/blogs")
@AllArgsConstructor
public class BlogController {
    @Autowired
    private final BlogActionService blogService;

    @GetMapping
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPublicBlogs() {
        return blogService.getALlPublicBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(@PathVariable("id") Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(@RequestBody BlogAddIn blogAddIn) {
        return blogService.addBlog(blogAddIn);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog(@PathVariable Long id, @RequestBody BlogUpdateIn blogUpdateIn) {
        return blogService.updateBlog(blogUpdateIn, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<String>> deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }

    @GetMapping("/personal")
    public ResponseEntity<SystemResponse<List<BlogOut>>> getPersonalBlogs() {
        return blogService.getAllPersonalBlog();
    }

    @GetMapping("/search")
    public ResponseEntity<SystemResponse<List<BlogOut>>> searchByTitleOrShortDescription(@RequestBody SearchBlogIn searchBlogIn) {
        return blogService.findByTitleOrShortDescription(searchBlogIn);
    }


}
