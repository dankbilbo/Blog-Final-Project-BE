package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
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
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPublicBlogs(){
        return blogService.getALlPublicBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(@PathVariable("id") Long id){
        return blogService.getBlogById(id);
    }

    @PostMapping
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(@RequestBody BlogAddIn blogAddIn){
        return blogService.addBlog(blogAddIn);
    }



}
