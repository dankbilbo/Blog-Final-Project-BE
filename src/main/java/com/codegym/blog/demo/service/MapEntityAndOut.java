package com.codegym.blog.demo.service;

import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.Tag;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityIn.BlogUpdateIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.repository.TagRepository;
import com.codegym.blog.demo.service.Interface.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MapEntityAndOut {

    @Autowired
    private final TagService tagService;

    public List<UserOut> mapListUserEntityAndOut(List<User> users) {
        List<UserOut> userOuts = new ArrayList<>();
        for (User user : users) {
            UserOut userOut = new UserOut(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    user.getCreatedAt()
            );
            userOuts.add(userOut);
        }
        return userOuts;
    }

    public UserOut mapUserEntityAndOut(User user) {
        UserOut userOut = new UserOut(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
        return userOut;
    }

    public List<BlogOut> mapListBlogEntityAndOut(List<Blog> blogs) {
        List<BlogOut> blogOuts = new ArrayList<>();
        for (Blog blog : blogs) {
            BlogOut blogOut = new BlogOut();
            blogOut.setId(blog.getId());
            blogOut.setTitle(blog.getTitle());
            blogOut.setContent(blog.getContent());
            blogOut.setShortDescription(blog.getShortDescription());
            blogOut.setPreviewImageURl(blog.getPreviewImageURL());
            blogOut.setCreatedAt(blog.getCreatedAt());
            blogOut.setUserId(blog.getUser().getId());
            blogOut.setPrivacy(blog.isPrivacy());
            blogOut.setTags(setTagsToString(blog.getTags()));
            blogOut.setViews(blog.getViews());
            blogOuts.add(blogOut);
        }
        return blogOuts;
    }

    public BlogOut mapBlogEntityAndOut(Blog blog) {
        BlogOut blogOut = new BlogOut();
        blogOut.setId(blog.getId());
        blogOut.setTitle(blog.getTitle());
        blogOut.setContent(blog.getContent());
        blogOut.setShortDescription(blog.getShortDescription());
        blogOut.setPreviewImageURl(blog.getPreviewImageURL());
        blogOut.setUserId(blog.getUser().getId());
        blogOut.setCreatedAt(blog.getCreatedAt());
        blogOut.setTags(setTagsToString(blog.getTags()));
        blogOut.setViews(blog.getViews());
        blogOut.setPrivacy(blog.isPrivacy());
        blogOut.setCategoryId(blog.getCategory().getId());
        return blogOut;
    }

    public Blog mapBlogAddInAndEntity(BlogAddIn blogAddIn, User user, Category category) {
        Blog blog = new Blog();
        blog.setTitle(blogAddIn.getTitle());
        blog.setContent(blogAddIn.getContent());
        blog.setShortDescription(blogAddIn.getShortDescription());
        blog.setPreviewImageURL(blogAddIn.getPreviewImageURL());
        blog.setCategory(category);
        blog.setPrivacy(blogAddIn.isPrivacy());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setTags(getTagBlog(blogAddIn.getTags()));
        blog.setUser(user);
        return blog;
    }

    public Blog mapBlogUpdateInAndEntity(BlogUpdateIn blogUpdateIn, Blog blog) {
        blog.setTitle(blogUpdateIn.getTitle());
        blog.setContent(blogUpdateIn.getContent());
        blog.setShortDescription(blogUpdateIn.getShortDescription());
        blog.setPreviewImageURL(blogUpdateIn.getPreviewImageURL());
        blog.setPrivacy(blogUpdateIn.isPrivacy());
        return blog;
    }

    Set<Tag> getTagBlog(String tags) {
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

    String setTagsToString(Set<Tag> tags) {
        ArrayList<String> tagNameCollect = new ArrayList<>();
        for (Tag tag : tags) {
            tagNameCollect.add(tag.getName());
        }
        return String.join(",", tagNameCollect);

    }
}
