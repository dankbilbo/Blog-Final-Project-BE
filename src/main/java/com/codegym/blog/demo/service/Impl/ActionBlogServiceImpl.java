package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ActionBlogServiceImpl implements BlogActionservice {
    @Autowired

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog() {
        return null;
    }
}
