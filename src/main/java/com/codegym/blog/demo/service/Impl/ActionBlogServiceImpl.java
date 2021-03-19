package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.Response;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import com.codegym.blog.demo.service.Interface.BlogService;
import com.codegym.blog.demo.service.Interface.TagService;
import com.codegym.blog.demo.service.MapEntityAndOut;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActionBlogServiceImpl implements BlogActionService {
    @Autowired
    private final BlogService blogService;

    @Autowired
    private final TagService tagService;

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog() {
        return null;
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs() {
        List<Blog> publicBlogs = blogService.findALlPublicBlogs();
        if (blogService.findALlPublicBlogs().isEmpty()) {
            return Response.not_found(StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(publicBlogs, new ArrayList<>());
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPrivateBlog() {
        return null;
    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id) {

        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            return Response.not_found(StringResponse.BLOG_NOT_FOUND);
        }
        BlogOut blogOut = MapEntityAndOut.mapBlogEntityAndOut(blog.get(), new BlogOut());
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn) {
        return  null;
    }


}
