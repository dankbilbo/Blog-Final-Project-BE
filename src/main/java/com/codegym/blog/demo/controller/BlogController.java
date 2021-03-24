package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.Status;
import com.codegym.blog.demo.model.in.*;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.CommentOut;
import com.codegym.blog.demo.model.out.StatusOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/blogs")
@AllArgsConstructor
public class BlogController {
    @Autowired
    private BlogActionService blogService;

    @GetMapping
    public ResponseEntity<SystemResponse<List<BlogOut>>> getAllPublicBlogs() {
        return blogService.getALlPublicBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<BlogOut>> getBlogById(@PathVariable("id") Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public ResponseEntity<SystemResponse<BlogOut>> addBlog(@RequestBody BlogAddIn blogAddIn) {
        return blogService.addBlog(blogAddIn);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<BlogOut>> updateBlog(@PathVariable Long id, @RequestBody BlogUpdateIn blogUpdateIn) {
        return blogService.updateBlog(blogUpdateIn, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<String>> deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }

    @GetMapping("/search")
    public ResponseEntity<SystemResponse<List<BlogOut>>> searchByTitleOrShortDescription(@RequestBody SearchBlogIn searchBlogIn) {
        return blogService.findByTitleOrShortDescription(searchBlogIn);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<SystemResponse<CommentOut>> comment(@PathVariable Long id,@RequestBody CommentIn commentIn){
        return blogService.comment(id,commentIn);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<SystemResponse<List<CommentOut>>> getAllBlogComments(@PathVariable Long id){
        return blogService.getAllBlogComment(id);
    }

    @DeleteMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<SystemResponse<String>> deleteComment(
            @PathVariable(name = "blogId") Long blogId,
            @PathVariable(name = "commentId") Long commentId){
        return blogService.deleteComment(blogId,commentId);
    }

    @PatchMapping("/{blogId}/comments/{commentId}")
    public ResponseEntity<SystemResponse<CommentOut>> updateComment(
            @PathVariable(name = "blogId") Long blogId,
            @PathVariable(name = "commentId") Long commentId, @RequestBody CommentUpdateIn commentUpdateIn){
        return blogService.updateComment(blogId,commentId,commentUpdateIn);
    }

    @GetMapping("/toplikes")
    public ResponseEntity<SystemResponse<List<BlogOut>>> getTop5Views() {
        return blogService.getTopFiveViewsBlogs();
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<SystemResponse<String>> likeBlog(@RequestBody StatusIn statusIn,@PathVariable Long id){
        return blogService.likeBlog(statusIn,id);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<SystemResponse<List<StatusOut>>> getAllLikesBlog(@PathVariable Long id){
        return blogService.getAllLikesBlog(id);
    }

}
