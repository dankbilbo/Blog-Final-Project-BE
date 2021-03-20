package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.model.Response;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.UserActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserActionService userActionService;

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<UserOut>> getUser(@PathVariable Long id){
        return userActionService.getUserById(id);
    }
}
