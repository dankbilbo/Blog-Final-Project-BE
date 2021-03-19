package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogActionService {
    ResponseEntity<SystemResponse<BlogOut>> updateBlog(BlogAddIn blogAddIn, Long id);
    ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs();
    ResponseEntity<SystemResponse<List<BlogOut>>> getAllPersonalBlog();
    ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id);
    ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn);
    ResponseEntity<SystemResponse<String>> deleteBlog(Long id);
}
