package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.Tag;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.in.BlogAddIn;
import com.codegym.blog.demo.model.in.BlogUpdateIn;
import com.codegym.blog.demo.model.in.SearchBlogIn;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.response.Response;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.repository.BlogRepository;
import com.codegym.blog.demo.repository.CategoryRepository;
import com.codegym.blog.demo.repository.UserRepository;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import com.codegym.blog.demo.service.Interface.TagService;
import com.codegym.blog.demo.model.mapper.MapEntityAndOut;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ActionBlogServiceImpl implements BlogActionService {

    @Autowired
    private final BlogRepository blogRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final TagService tagService;


    @Override
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog(BlogUpdateIn blogUpdateIn, Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        User user = blog.get().getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.getUsername());

        if (!(correctUser && isAdmin)) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }

        Blog blogToUpdate = MapEntityAndOut.mapBlogUpdateInAndEntity(blogUpdateIn,blog.get());
        Blog blogAfterUpdate = blogRepository.save(blogToUpdate);

        BlogOut blogOut = MapEntityAndOut.mapBlogEntityAndOut(blogAfterUpdate);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.BLOD_UPDATED, blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs() {
        List<Blog> publicBlogs = blogRepository.findAllByPrivacyIsTrue();
        if (blogRepository.findAllByPrivacyIsTrue().isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(publicBlogs);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPersonalBlog() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Blog> blogs = blogRepository.findAllByUser_Username(username);
        if (blogs.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(blogs);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);

    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);

        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        User user = blog.get().getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.getUsername());

        if (!(correctUser && isAdmin) && blog.get().isPrivacy() == false) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }

        if (!username.equals(user.getUsername())){
             blog.get().setViews(blog.get().getViews() + 1);

        }
        Blog blogAfterView = blogRepository.save(blog.get());

        BlogOut blogOut = MapEntityAndOut.mapBlogEntityAndOut(blogAfterView);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(username);

        Optional<Category> category = categoryRepository.findById(blogAddIn.getIdCategory());
        Category categoryAdded = !category.isPresent()
                ? categoryRepository.save(new Category("General", LocalDateTime.now())) : category.get();

        Set<Tag> blogTags = getTagBlog(blogAddIn.getTags());

        Blog blog = MapEntityAndOut.mapBlogAddInAndEntity(blogAddIn, user.get(), categoryAdded, blogTags);
        blog = blogRepository.save(blog);

        BlogOut blogOut = MapEntityAndOut.mapBlogEntityAndOut(blog);
        return Response.created(ErrorCodeMessage.CREATED, StringResponse.BLOG_ADDED, blogOut);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> deleteBlog(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        User user = blog.get().getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.getUsername());

        if (!(correctUser && isAdmin)) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }

        blogRepository.deleteById(id);
        return Response.no_content(ErrorCodeMessage.NO_CONTENT, StringResponse.BLOG_DELETED);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> findByTitleOrShortDescription(SearchBlogIn searchBlogIn) {
        List<Blog> searchedBlog = blogRepository.search("%" + searchBlogIn.getSearchKey() + "%");
        if (searchedBlog.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(searchedBlog);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);
    }

    private Set<Tag> getTagBlog(String tags) {
        Set<String> tagsSplit = new HashSet<>(Arrays.asList(tags.split(",")));
        Set<Tag> blogTags = new HashSet<>();
        for (String tag : tagsSplit) {
            boolean tagExistedInDB = tagService.findByName(tag).isPresent();
            if (!tagExistedInDB) {
                tagService.save(new Tag(tag, LocalDateTime.now()));
            }
            blogTags.add(tagService.findByName(tag).get());
        }
        return blogTags;
    }




}
