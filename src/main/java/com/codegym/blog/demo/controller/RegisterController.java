package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.in.UserSignUp;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<SystemResponse<String>> register(@RequestBody UserSignUp userSignUp){
        return userService.signUp(userSignUp);
    }

    @GetMapping("/{token}")
    public ResponseEntity<SystemResponse<String>> verify(@PathVariable String token){
        return userService.verify(token);
    }


}
