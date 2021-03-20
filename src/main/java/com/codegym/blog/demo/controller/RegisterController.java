package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.EntityIn.UserSignUp;
import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.service.ActionService.UserActionService;
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
    private final UserActionService userActionService;

    @PostMapping
    public ResponseEntity<SystemResponse<UserOut>> register(@RequestBody UserSignUp userSignUp){
        return userActionService.signUp(userSignUp);
    }


}
