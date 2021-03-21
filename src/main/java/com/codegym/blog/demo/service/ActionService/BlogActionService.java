package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.in.BlogAddIn;
import com.codegym.blog.demo.model.in.BlogUpdateIn;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogActionService {
    ResponseEntity<SystemResponse<BlogOut>> updateBlog(BlogUpdateIn blogUpdateIn, Long id);
    ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs();
    ResponseEntity<SystemResponse<List<BlogOut>>> getAllPersonalBlog();
    ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id);
    ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn);
    ResponseEntity<SystemResponse<String>> deleteBlog(Long id);
}
