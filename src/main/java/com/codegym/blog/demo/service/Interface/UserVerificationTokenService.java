package com.codegym.blog.demo.service.Interface;


import com.codegym.blog.demo.model.Entity.UserVerificationToken;

import java.util.Optional;

public interface UserVerificationTokenService extends Service<UserVerificationToken>{
    Optional<UserVerificationToken> getToken(String token);
    void verifyToken(String token);
}
