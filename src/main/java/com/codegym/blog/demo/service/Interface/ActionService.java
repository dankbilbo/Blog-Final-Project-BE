package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.EntityIn.UserSignUp;
import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.model.SystemResponse;
import org.springframework.http.ResponseEntity;

public interface ActionService {
    ResponseEntity<SystemResponse<UserOut>> signUp(UserSignUp userSignUp);
}
