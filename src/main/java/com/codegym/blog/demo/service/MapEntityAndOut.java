package com.codegym.blog.demo.service;

import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.Tag;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.EntityIn.BlogAddIn;
import com.codegym.blog.demo.model.EntityOut.BlogOut;
import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MapEntityAndOut {
    @Autowired
    private static TagRepository tagRepository;

    public static List<UserOut> mapListUserEntityAndOut(List<User> users, List<UserOut> userOuts) {
        return userOuts;
    }

    public static List<BlogOut> mapListBlogEntityAndOut(List<Blog> blogs, ArrayList<BlogOut> blogOuts) {
        for (Blog blog : blogs) {
            BlogOut blogOut = new BlogOut();
            blogOut.setId(blog.getId());
            blogOut.setTitle(blog.getTitle());
            blogOut.setContent(blog.getContent());
            blogOut.setShortDescription(blog.getShortDescription());
            blogOut.setPreviewImageURl(blog.getPreviewImageURL());
            blogOut.setCreatedAt(blog.getCreatedAt());
            blogOut.setUserId(blog.getUser().getId());
            blogOuts.add(blogOut);
        }
        return blogOuts;
    }

    public static BlogOut mapBlogEntityAndOut(Blog blog, BlogOut blogOut) {
        blogOut.setId(blog.getId());
        blogOut.setTitle(blog.getTitle());
        blogOut.setContent(blog.getContent());
        blogOut.setShortDescription(blog.getShortDescription());
        blogOut.setPreviewImageURl(blog.getPreviewImageURL());
        blogOut.setUserId(blog.getUser().getId());
        blogOut.setCreatedAt(blog.getCreatedAt());
        blogOut.setTags(setTagsToString(blog.getTags()));
        return blogOut;
    }

    public static Blog mapBlogInAndEntity(BlogAddIn blogAddIn, Blog blog, User user, Category category) {
        blog.setTitle(blogAddIn.getTitle());
        blog.setContent(blogAddIn.getContent());
        blog.setShortDescription(blogAddIn.getShortDescription());
        blog.setPreviewImageURL(blogAddIn.getPreviewImageURL());
        blog.setCategory(category);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setTags(getTagBlog(blogAddIn.getTags()));
        blog.setUser(user);
        return blog;
    }

    static Set<Tag> getTagBlog(String tags) {
        Set<String> tagsSplit = new HashSet<>(Arrays.asList(tags.split(",")));
        Set<Tag> blogTags = new HashSet<>();
        for (String tag : tagsSplit) {
            boolean tagExistedInDB = tagRepository.findByName(tag).isPresent();
            if (!tagExistedInDB) {
                tagRepository.save(new Tag(tag, LocalDateTime.now()));
            }
            blogTags.add(tagRepository.findByName(tag).get());
        }
        return blogTags;
    }

    static String setTagsToString(Set<Tag> tags) {
        ArrayList<String> tagNameCollect = new ArrayList<>();
        for (Tag tag : tags) {
            tagNameCollect.add(tag.getName());
        }
        return String.join(",", tagNameCollect);

    }
}
