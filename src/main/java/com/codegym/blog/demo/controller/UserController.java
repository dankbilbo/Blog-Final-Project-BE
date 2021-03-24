package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.BlogActionService;
import com.codegym.blog.demo.service.ActionService.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BlogActionService blogService;

    @GetMapping
    public ResponseEntity<SystemResponse<List<UserOut>>> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<UserOut>> getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<UserOut>> updateUserProfile(@PathVariable Long id, @RequestBody UserUpdateIn userUpdateIn) {
        return userService.updateUser(userUpdateIn, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<String>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<SystemResponse<UserPasswordOut>> updateUserProfile(@PathVariable Long id, @RequestBody UserPasswordIn userPasswordIn) {
        return userService.changePassword(userPasswordIn, id);
    }

    @GetMapping("/{id}/blogs")
    public ResponseEntity<SystemResponse<List<BlogOut>>> getPersonalBlogs() {
        return blogService.getAllPersonalBlog();
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<SystemResponse<String>> blockUser(@PathVariable Long id) {
        return userService.blockUser(id);
    }

//    @GetMapping
//    public ResponseEntity<SystemResponse<List<BlogOut>>> findSpecificPersonalBlogs() {
//        return null;
//    }
}
