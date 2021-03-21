package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.Entity.User;

import java.util.Optional;

public interface UserService extends Service<User>{
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
}
