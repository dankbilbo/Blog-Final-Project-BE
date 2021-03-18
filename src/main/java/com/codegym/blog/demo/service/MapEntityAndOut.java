package com.codegym.blog.demo.service;

import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.EntityOut.UserOut;

import java.util.ArrayList;
import java.util.List;

public class MapEntityAndOut {
    public static List<UserOut> mapListUserEntityAndOut(List<User> users,List<UserOut> userOuts){
        return userOuts;
    }

    public static List<BlogOut> mapListBlogEntityAndOut(List<Blog> blogs, ArrayList<BlogOut> blogOuts){
        for (Blog blog : blogs){
            BlogOut blogOut = new BlogOut();
            blogOut.setTitle(blog.getTitle());
            blogOut.setContent(blog.getContent());
            blogOut.setShortDescription(blog.getShortDescription());
            blogOut.setPreviewImageURl(blog.getPreviewImageURL());
            blogOut.setCreatedAt(blog.getCreatedAt());
            blogOuts.add(blogOut);
        }
        return blogOuts;
    }
}
