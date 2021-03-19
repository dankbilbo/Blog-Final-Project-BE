package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.Response;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import com.codegym.blog.demo.service.Interface.BlogService;
import com.codegym.blog.demo.service.Interface.CategoryService;
import com.codegym.blog.demo.service.Interface.UserService;
import com.codegym.blog.demo.service.MapEntityAndOut;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActionBlogServiceImpl implements BlogActionService {
    @Autowired
    private final BlogService blogService;

    @Autowired
    private final MapEntityAndOut mapEntityAndOut;

    @Autowired
    private final UserService userService;

    @Autowired
    private final CategoryService categoryService;


    @Override
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog(BlogAddIn blogAddIn, Long id) {
        boolean blogExisted = blogService.findById(id).isPresent();
        if (!blogExisted){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.BLOG_NOT_FOUND);
        }
        //TODO : check user

        Optional<Category> category = categoryService.findById(blogAddIn.getIdCategory());

//        Blog blog = mapEntityAndOut.mapBlogInAndEntity(blogAddIn,)
        BlogOut blogOut = new BlogOut();
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.BLOD_UPDATED,blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs() {
        List<Blog> publicBlogs = blogService.findALlPublicBlogs();
        if (blogService.findALlPublicBlogs().isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = mapEntityAndOut.mapListBlogEntityAndOut(publicBlogs);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPersonalBlog() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Blog> blogs = blogService.findAllByUsername(username);
        if (blogs.isEmpty()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_FOUND);
        }

        List<BlogOut> blogOuts = mapEntityAndOut.mapListBlogEntityAndOut(blogs);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id) {
        Optional<Blog> blog = blogService.findById(id);

        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.BLOG_NOT_FOUND);
        }

        User user = blog.get().getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();

        if (!username.equals(user.getUsername())
                && blog.get().isPrivacy() == false
                && !user.getRole().stream().noneMatch(userRole -> !userRole.getRoleName().equals("ADMIN"))){
            return Response.not_authorized(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_AUTHORIZED);
        }

        BlogOut blogOut = mapEntityAndOut.mapBlogEntityAndOut(blog.get());
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUsername(username);

        Optional<Category> category = categoryService.findById(blogAddIn.getIdCategory());
        Category categoryAdded = !category.isPresent()
                ? categoryService.save(new Category("General", LocalDateTime.now())) : category.get();

        Blog blog = mapEntityAndOut.mapBlogInAndEntity(blogAddIn,user.get(),categoryAdded);
        blog = blogService.save(blog);

        BlogOut blogOut = mapEntityAndOut.mapBlogEntityAndOut(blog);
        return  Response.created(ErrorCodeMessage.CREATED,StringResponse.BLOG_ADDED,blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> deleteBlog(Long id) {
        Optional<Blog> blog = blogService.findById(id);

        if (blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.BLOG_NOT_FOUND);
        }

        User user = blog.get().getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username  = authentication.getName();
        if (!username.equals(user.getUsername())
                && blog.get().isPrivacy() == false
                && !user.getRole().stream().noneMatch(userRole -> !userRole.getRoleName().equals("ADMIN"))){
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED,StringResponse.NOT_AUTHORIZED);
        }

        blogService.deleteById(id);
        return Response.no_content(ErrorCodeMessage.NO_CONTENT,StringResponse.BLOG_DELETED);
    }

//    checkUser(Optional<Blog> blogOptional){
//
//    }


}
