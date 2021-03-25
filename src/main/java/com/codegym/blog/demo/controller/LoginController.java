package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.JwtResponse;
import com.codegym.blog.demo.model.Entity.UserPrincipal;
import com.codegym.blog.demo.model.in.UserEmailIn;
import com.codegym.blog.demo.model.in.UserLogin;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.security.JwtService;
import com.codegym.blog.demo.service.ActionService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {

        return jwtService.login(userLogin);
    }

    @PostMapping("/reclaim")
    public ResponseEntity<SystemResponse<String>> reclaimPassword(@RequestBody UserEmailIn userEmailIn) {
        return userService.reclaimPassword(userEmailIn);
    }

    @PostMapping("/reclaim/{token}")
    public ResponseEntity<SystemResponse<String>> changePasswordAfterIdentify(@PathVariable String token, UserPasswordIn userPasswordIn){
        return userService.changePasswordAfterIdentify(token,userPasswordIn);
    }
}
