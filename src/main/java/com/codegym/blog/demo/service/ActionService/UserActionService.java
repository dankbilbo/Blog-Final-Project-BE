package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserSignUp;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import org.springframework.http.ResponseEntity;

public interface UserActionService {
    ResponseEntity<SystemResponse<UserOut>> signUp(UserSignUp userSignUp);

    ResponseEntity<SystemResponse<UserOut>> getUserById(Long id);

    ResponseEntity<SystemResponse<UserOut>> updateUser(UserUpdateIn userUpdateIn,Long id);

    ResponseEntity<SystemResponse<String>> deleteUser(Long id);

    ResponseEntity<SystemResponse<UserPasswordOut>> changePassword(UserPasswordIn userPasswordIn, Long id);
}
