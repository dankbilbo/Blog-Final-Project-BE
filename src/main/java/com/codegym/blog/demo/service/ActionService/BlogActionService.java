package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogActionService {
    ResponseEntity<SystemResponse<BlogOut>> updateBlog();
    ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs();
}
