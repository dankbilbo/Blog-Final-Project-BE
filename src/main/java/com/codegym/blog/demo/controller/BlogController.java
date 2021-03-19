package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/blogs")
@AllArgsConstructor
public class BlogController {
    @Autowired
    private final ActionService actionService;

//    public ResponseEntity<SystemResponse<BlogOut>> getAllPublicBlogs(){
//        return actionService.getALlPublicBlogs();
//    }
}
