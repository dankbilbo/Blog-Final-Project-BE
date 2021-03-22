package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<SystemResponse<List<UserOut>>> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<UserOut>> getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SystemResponse<UserOut>> updateUserProfile(@PathVariable Long id, @RequestBody UserUpdateIn userUpdateIn){
        return userService.updateUser(userUpdateIn,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<String>> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<SystemResponse<UserPasswordOut>> updateUserProfile(@PathVariable Long id, @RequestBody UserPasswordIn userPasswordIn){
        return userService.changePassword(userPasswordIn,id);
    }
}
