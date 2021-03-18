package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.Entity.Blog;

import java.util.List;

public interface BlogService extends Service<Blog>{
    List<Blog> findALlPublicBlogs();
}
