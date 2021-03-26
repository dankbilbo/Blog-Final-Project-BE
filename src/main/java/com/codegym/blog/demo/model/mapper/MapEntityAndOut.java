package com.codegym.blog.demo.model.mapper;

import com.codegym.blog.demo.model.Entity.*;
import com.codegym.blog.demo.model.in.*;
import com.codegym.blog.demo.model.out.*;
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
            userOuts.add(mapUserEntityAndOut(user));
        }
        return userOuts;
    }

    public static User mapUserPasswordInAndEntity(UserPasswordIn userPasswordIn, User user) {
        user.setPassword(userPasswordIn.getPassword());
        return user;
    }

    public static UserPasswordOut mapUserPasswordAndOut(User user) {
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
                user.getCreatedAt(),
                user.isLocked()
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
            blogOuts.add(mapBlogEntityAndOut(blog));
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
        blogOut.setUsername(blog.getUser().getUsername());
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

    public static CommentOut mapCommentEntityAndOut(Comment comment) {
        return new CommentOut(
                comment.getId(),
                comment.getContent(),
                comment.getBlog().getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }

    public static Comment mapCommentInAndEntity(CommentIn commentIn, Blog blog, User user) {
        return new Comment(
                commentIn.getContent(),
                LocalDateTime.now(),
                user,
                blog
        );
    }

    public static Comment mapCommentUpdateInAndEntity(CommentUpdateIn commentUpdateIn,Comment comment){
         comment.setContent(commentUpdateIn.getContent());
         return comment;
    }

    public static List<CommentOut> mapListCommentEntityAndOut(List<Comment> comments) {
        List<CommentOut> commentOuts = new ArrayList<>();
        for (Comment comment : comments){
            commentOuts.add(mapCommentEntityAndOut(comment));
        }
        return commentOuts;
    }

    public static List<StatusOut> mapListStatusEntityAndOUt(List<Status> statuses){
        List<StatusOut> statusOuts = new ArrayList<>();
        for (Status status : statuses){
            StatusOut statusOut = new StatusOut(
                    status.getId(),
                    status.isLiked(),
                    status.getUser().getId(),
                    status.getBlog().getId()
            );
            statusOuts.add(statusOut);
        }
        return statusOuts;
    }


}
