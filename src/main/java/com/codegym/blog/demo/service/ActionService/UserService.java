package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.in.*;
import com.codegym.blog.demo.model.out.BlogOut;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<SystemResponse<List<UserOut>>> getAllUser();

    ResponseEntity<SystemResponse<String>> signUp(UserSignUp userSignUp);

    ResponseEntity<SystemResponse<UserOut>> getUserById(Long id);

    ResponseEntity<SystemResponse<UserOut>> updateUser(UserUpdateIn userUpdateIn, Long id);

    ResponseEntity<SystemResponse<String>> deleteUser(Long id);

    ResponseEntity<SystemResponse<UserPasswordOut>> changePassword(UserPasswordIn userPasswordIn, Long id);

    ResponseEntity<SystemResponse<String>> verify(String token);

    ResponseEntity<SystemResponse<String>> blockUser(UserBanIn userBanIn, Long id);

    ResponseEntity<SystemResponse<String>> reclaimPassword(UserEmailIn userEmailIn);

    ResponseEntity<SystemResponse<String>> changePasswordAfterIdentify(String token, UserPasswordIn userPasswordIn);
}
