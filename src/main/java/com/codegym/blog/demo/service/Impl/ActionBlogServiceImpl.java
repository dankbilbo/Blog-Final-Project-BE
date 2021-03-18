package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.Response;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import com.codegym.blog.demo.service.Interface.BlogService;
import com.codegym.blog.demo.service.MapEntityAndOut;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActionBlogServiceImpl implements BlogActionService {
    @Autowired
    private final BlogService blogService;

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog() {
        return null;
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs() {
        List<Blog> publicBlogs = blogService.findALlPublicBlogs();
        if (blogService.findALlPublicBlogs().isEmpty()){
            return Response.not_found(StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(publicBlogs,new ArrayList<>());
            return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,blogOuts);
    }
}
