package com.codegym.blog.demo.model.mapper;

import com.codegym.blog.demo.model.Entity.Blog;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.Tag;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.in.BlogAddIn;
import com.codegym.blog.demo.model.in.BlogUpdateIn;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MapEntityAndOut {

    public static List<UserOut> mapListUserEntityAndOut(List<User> users) {
        List<UserOut> userOuts = new ArrayList<>();
        for (User user : users) {
            UserOut userOut = new UserOut(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getAvatarURL(),
                    user.getRole(),
                    user.getCreatedAt()
            );
            userOuts.add(userOut);
        }
        return userOuts;
    }

    public static User mapUserPasswordInAndEntity(UserPasswordIn userPasswordIn, User user) {
        user.setPassword(userPasswordIn.getPassword());
        return user;
    }

    public static UserPasswordOut mapUserPasswordAndOut(User user){
        return new UserPasswordOut(user.getPassword());
    }

    public static UserOut mapUserEntityAndOut(User user) {
        UserOut userOut = new UserOut(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatarURL(),
                user.getRole(),
                user.getCreatedAt()
        );
        return userOut;
    }

    public static User mapUserUpdateInAndUserEntity(UserUpdateIn userUpdateIn, User user) {
        user.setFirstName(userUpdateIn.getFirstName());
        user.setLastName(userUpdateIn.getLastName());
        user.setAvatarURL(userUpdateIn.getAvatarURL());
        user.setEmail(userUpdateIn.getEmail());
        return user;
    }

    public static List<BlogOut> mapListBlogEntityAndOut(List<Blog> blogs) {
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
            blogOut.setCategoryId(blog.getCategory().getId());
            blogOuts.add(blogOut);
        }
        return blogOuts;
    }

    public static BlogOut mapBlogEntityAndOut(Blog blog) {
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

    public static Blog mapBlogAddInAndEntity(BlogAddIn blogAddIn, User user, Category category, Set<Tag> tags) {
        Blog blog = new Blog();
        blog.setTitle(blogAddIn.getTitle());
        blog.setContent(blogAddIn.getContent());
        blog.setShortDescription(blogAddIn.getShortDescription());
        blog.setPreviewImageURL(blogAddIn.getPreviewImageURL());
        blog.setCategory(category);
        blog.setPrivacy(blogAddIn.isPrivacy());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setTags(tags);
        blog.setUser(user);
        return blog;
    }

    public static Blog mapBlogUpdateInAndEntity(BlogUpdateIn blogUpdateIn, Blog blog) {
        blog.setTitle(blogUpdateIn.getTitle());
        blog.setContent(blogUpdateIn.getContent());
        blog.setShortDescription(blogUpdateIn.getShortDescription());
        blog.setPreviewImageURL(blogUpdateIn.getPreviewImageURL());
        blog.setPrivacy(blogUpdateIn.isPrivacy());
        return blog;
    }

    private static String setTagsToString(Set<Tag> tags) {
        ArrayList<String> tagNameCollect = new ArrayList<>();
        for (Tag tag : tags) {
            tagNameCollect.add(tag.getName());
        }
        return String.join(",", tagNameCollect);
    }


}
