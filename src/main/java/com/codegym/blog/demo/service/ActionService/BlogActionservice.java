package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import org.springframework.http.ResponseEntity;

public interface BlogActionservice {
    ResponseEntity<SystemResponse<BlogOut>> updateBlog();
}
