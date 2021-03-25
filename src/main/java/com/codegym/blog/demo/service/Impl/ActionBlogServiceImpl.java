package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.*;
import com.codegym.blog.demo.model.in.*;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.CommentOut;
import com.codegym.blog.demo.model.out.StatusOut;
import com.codegym.blog.demo.model.response.Response;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.repository.*;
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
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StatusRepository statusRepository;


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
            return Response.not_authorized(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }

        Blog blogToUpdate = MapEntityAndOut.mapBlogUpdateInAndEntity(blogUpdateIn, blog.get());
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
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }

        if (!username.equals(user.getUsername())) {
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
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }


        List<Comment> comments = commentRepository.findAllByBlog_Id(blog.get().getId());
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                deleteCommentInDb(comment);
            }
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

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> findSpecificPersonalBlogs(SearchBlogIn searchBlogIn) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Blog> blogs = blogRepository.findAllByUser_UsernameAndTitleContaining(username, searchBlogIn.getSearchKey());
        if (blogs.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(blogs);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<CommentOut>> comment(Long blogid, CommentIn commentIn) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);

        Optional<Blog> blog = blogRepository.findById(blogid);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        boolean isBlogPublic = blog.get().isPrivacy();
        boolean isUserWhoWroteBlog = blog.get().getUser().getUsername().equals(username);
        boolean isAdmin = user.get().getRole().stream().anyMatch(userRole -> userRole.getRoleName().equals("ADMIN"));

        if (isBlogPublic || isUserWhoWroteBlog || isAdmin) {
            Comment repliedTo = null;
            if (commentIn.getCommentId() != null) {
                boolean repliedToIsPresent = commentRepository.findById(commentIn.getCommentId()).isPresent();
                if (!repliedToIsPresent){
                    return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.NOT_FOUND);
                }

                repliedTo = commentRepository.findById(commentIn.getCommentId()).get();
            }

            Comment commentEntity = commentRepository.save(MapEntityAndOut.mapCommentInAndEntity(commentIn, blog.get(), user.get(), repliedTo));

            CommentOut commentOut = MapEntityAndOut.mapCommentEntityAndOut(commentEntity);
            return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, commentOut);
        }

        return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
    }

    @Override
    public ResponseEntity<SystemResponse<List<CommentOut>>> getAllBlogComment(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        List<Comment> comments = commentRepository.findAllByBlog_Id(id);
        if (comments.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.NOT_FOUND);
        }

        List<CommentOut> commentOuts = MapEntityAndOut.mapListCommentEntityAndOut(comments);

        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, commentOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> deleteComment(Long blogId, Long commentId) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (!commentRepository.findById(commentId).isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.NOT_FOUND);
        }

        deleteCommentInDb(comment.get());

        return Response.no_content(ErrorCodeMessage.NO_CONTENT, StringResponse.COMMENT_DELETED);
    }

    @Override
    public ResponseEntity<SystemResponse<CommentOut>> updateComment(Long blogId, Long commentId, CommentUpdateIn commentUpdateIn) {
        Optional<Blog> blog = blogRepository.findById(blogId);
        if (!blog.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (!commentRepository.findById(commentId).isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.NOT_FOUND);
        }

        Comment commentEntity = MapEntityAndOut.mapCommentUpdateInAndEntity(commentUpdateIn, comment.get());
        commentEntity = commentRepository.save(commentEntity);
        CommentOut commentOut = MapEntityAndOut.mapCommentEntityAndOut(commentEntity);

        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, commentOut);
    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getTopFiveViewsBlogs() {
        List<Blog> blogs = blogRepository.find5MostViewsPublicBlogs();
        if (blogs.isEmpty()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(blogs);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,blogOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> likeBlog(StatusIn statusIn, Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }

        boolean isBlogPrivate = !blog.get().isPrivacy();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (isBlogPrivate && !user.equals(blog.get().getUser())){
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN,StringResponse.FORBIDDEN);
        }
        Optional<Status> statusToCheck = statusRepository.findAllByUser_IdAndBlog_Id(user.get().getId(),blog.get().getId());
        if (statusToCheck.isPresent()){
            statusToCheck.get().setLiked(!statusToCheck.get().isLiked());
            statusRepository.save(statusToCheck.get());
        }else {
            Status status = new Status(LocalDateTime.now(),user.get(),LocalDateTime.now(),true,blog.get());
            statusRepository.save(status);
        }

        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,StringResponse.OK);
    }

    @Override
    public ResponseEntity<SystemResponse<List<StatusOut>>> getAllLikesBlog(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (!blog.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        boolean isBlogPrivate = !blog.get().isPrivacy();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (isBlogPrivate && !user.equals(blog.get().getUser())){
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN,StringResponse.FORBIDDEN);
        }

        List<Status> statuses = statusRepository.findAllByBlog_Id(id);
        if (statuses.isEmpty()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.NOT_FOUND);
        }
        List<StatusOut> statusOuts = MapEntityAndOut.mapListStatusEntityAndOUt(statuses);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,statusOuts);

    }

    @Override
    public ResponseEntity<SystemResponse<List<BlogOut>>> getTop5Likes() {
        List<Blog> blogs = blogRepository.find5MostLikesPublicBlog();
        if (blogs.isEmpty()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.BLOG_NOT_FOUND);
        }
        List<BlogOut> blogOuts = MapEntityAndOut.mapListBlogEntityAndOut(blogs);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,blogOuts);
    }

    private void deleteCommentInDb(Comment comment) {
        List<Comment> replies = commentRepository.findAllByRepliedTo_Id(comment.getId());
        boolean doesntHasReply = commentRepository.findAllByRepliedTo_Id(comment.getId()).isEmpty();
        if (!doesntHasReply) {
            for (Comment reply : replies) {
                deleteCommentInDb(reply);
            }
        }
        commentRepository.delete(comment);
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
