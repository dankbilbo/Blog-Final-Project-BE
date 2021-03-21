package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.model.Entity.UserVerificationToken;
import com.codegym.blog.demo.repository.UserVerificationTokenRepository;
import com.codegym.blog.demo.service.Interface.UserVerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserVerificationTokenServiceImp implements UserVerificationTokenService {

    @Autowired
    private final UserVerificationTokenRepository verificationTokenRepository;


    @Override
    public Optional<UserVerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public void verifyToken(String token) {
        verificationTokenRepository.verifyToken(LocalDateTime.now(),token);
    }

    @Override
    public List<UserVerificationToken> findAll() {
        return null;
    }

    @Override
    public Optional<UserVerificationToken> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserVerificationToken save(UserVerificationToken userVerificationToken) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
