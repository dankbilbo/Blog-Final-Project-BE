package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.Entity.UserRole;
import com.codegym.blog.demo.model.EntityIn.UserSignUp;
import com.codegym.blog.demo.model.EntityOut.UserOut;
import com.codegym.blog.demo.model.Response;
import com.codegym.blog.demo.model.SystemResponse;
import com.codegym.blog.demo.repository.RoleRepository;
import com.codegym.blog.demo.security.PasswordEncoder;
import com.codegym.blog.demo.service.ActionService.ActionService;
import com.codegym.blog.demo.service.Interface.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserActionServiceImpl implements ActionService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<SystemResponse<UserOut>> signUp(UserSignUp userSignUp) {
        Optional<User> userFindByUsername = userService.findByUsername(userSignUp.getUsername());
        if (userFindByUsername.isPresent()){
            return Response.bad_request(StringResponse.USERNAME_EXISTED);
        }

        Optional<User> userFindByEmail = userService.findByEmail(userSignUp.getEmail());
        if (userFindByEmail.isPresent()){
            return Response.bad_request(StringResponse.EMAIL_EXISTED);
        }
        String userPassword = passwordEncoder.encoder().encode(userSignUp.getPassword());

        Set<UserRole> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("MEMBER").get());
        userService.save(
                new User(userSignUp.getUsername()
                        ,userPassword
                        ,userSignUp.getEmail()
                        , LocalDateTime.now()
                        , roles
                        ));
        return Response.ok(ErrorCodeMessage.CREATED
                ,StringResponse.REGISTERED
                ,new UserOut(userSignUp.getUsername(),userSignUp.getPassword(),userSignUp.getEmail()));
    }
}
