package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.SystemResponse;
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
    private final UserService userService;

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

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }
}
