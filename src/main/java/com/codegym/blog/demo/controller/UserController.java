package com.codegym.blog.demo.controller;

import com.codegym.blog.demo.service.ActionService.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {
    @Autowired
    private final ActionService actionService;

//    @GetMapping("/{id}")
//    public Response<ResponseEntity<UserOut>> getUser(@PathVariable Long id){
//
//    }
}
