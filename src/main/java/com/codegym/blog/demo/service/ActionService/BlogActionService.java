package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.Entity.Status;
import com.codegym.blog.demo.model.in.*;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.CommentOut;
import com.codegym.blog.demo.model.out.StatusOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogActionService {
    ResponseEntity<SystemResponse<BlogOut>> updateBlog(BlogUpdateIn blogUpdateIn, Long id);
    ResponseEntity<SystemResponse<List<BlogOut>>> getALlPublicBlogs();
    ResponseEntity<SystemResponse<List<BlogOut>>> getAllPersonalBlog();
    ResponseEntity<SystemResponse<BlogOut>> getBlogById(Long id);
    ResponseEntity<SystemResponse<BlogOut>> addBlog(BlogAddIn blogAddIn);
    ResponseEntity<SystemResponse<String>> deleteBlog(Long id);
    ResponseEntity<SystemResponse<List<BlogOut>>> findByTitleOrShortDescription(SearchBlogIn searchBlogIn);
    ResponseEntity<SystemResponse<List<BlogOut>>> findSpecificPersonalBlogs(SearchBlogIn searchBlogIn);
    ResponseEntity<SystemResponse<CommentOut>> comment(Long id, CommentIn commentIn);
    ResponseEntity<SystemResponse<List<CommentOut>>> getAllBlogComment(Long id);

    ResponseEntity<SystemResponse<String>> deleteComment(Long blogId, Long commentId);

    ResponseEntity<SystemResponse<CommentOut>> updateComment(Long blogId, Long commentId, CommentUpdateIn commentUpdateIn);

    ResponseEntity<SystemResponse<List<BlogOut>>> getTopFiveViewsBlogs();

    ResponseEntity<SystemResponse<String>> likeBlog(StatusIn status, Long id);

    ResponseEntity<SystemResponse<List<StatusOut>>> getAllLikesBlog(Long id);

    ResponseEntity<SystemResponse<List<BlogOut>>> getTop5Likes();

    ResponseEntity<SystemResponse<List<BlogOut>>> findALl();

    ResponseEntity<SystemResponse<List<BlogOut>>> findByTags(SearchBlogIn searchBlogIn);
}
