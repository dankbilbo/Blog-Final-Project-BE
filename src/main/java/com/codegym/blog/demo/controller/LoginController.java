package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.Entity.JwtResponse;
import com.codegym.blog.demo.model.Entity.UserPrincipal;
import com.codegym.blog.demo.model.in.UserLogin;
import com.codegym.blog.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody UserLogin userLogin) {
        System.out.println(userLogin);

        return jwtService.login(userLogin);
    }
//
//    @PostMapping("/login-facebook")
//    public ResponseEntity<?> loginWithFacebook(HttpRequest httpRequest) throws ClientProtocolException, IOException{
//
//    }
}
