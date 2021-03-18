package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
