package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.EntityIn.UserSignUp;
import com.codegym.blog.demo.model.Response;

import java.util.Optional;

public interface UserService extends Service<User> {
    Optional<User> findByEmail(String email);
}
