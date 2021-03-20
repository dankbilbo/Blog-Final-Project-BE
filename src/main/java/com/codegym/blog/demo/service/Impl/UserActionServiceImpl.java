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
import com.codegym.blog.demo.service.ActionService.UserActionService;
import com.codegym.blog.demo.service.Interface.UserService;
import com.codegym.blog.demo.service.MapEntityAndOut;
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
public class UserActionServiceImpl implements UserActionService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final MapEntityAndOut mapEntityAndOut;

    @Override
    public ResponseEntity<SystemResponse<UserOut>> signUp(UserSignUp userSignUp) {
        Optional<User> userFindByUsername = userService.findByUsername(userSignUp.getUsername());
        if (userFindByUsername.isPresent()){
            return Response.bad_request(ErrorCodeMessage.BAD_REQUEST,StringResponse.USERNAME_EXISTED);
        }

        Optional<User> userFindByEmail = userService.findByEmail(userSignUp.getEmail());
        if (userFindByEmail.isPresent()){
            return Response.bad_request(ErrorCodeMessage.BAD_REQUEST,StringResponse.EMAIL_EXISTED);
        }
        String userPassword = passwordEncoder.encoder().encode(userSignUp.getPassword());


        Optional<UserRole> roleMember = roleRepository.findByRoleName("MEMBER");
        if (!roleMember.isPresent()){
            roleRepository.save(new UserRole("MEMBER"));
        }

        Set<UserRole> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("MEMBER").get());

        User user = userService.save(
                new User(userSignUp.getUsername()
                        ,userPassword
                        ,userSignUp.getEmail()
                        , LocalDateTime.now()
                        , roles
                        ));

        UserOut userOut = mapEntityAndOut.mapUserEntityAndOut(user);

        return Response.ok(ErrorCodeMessage.CREATED
                ,StringResponse.REGISTERED
                ,userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<UserOut>> getUserById(Long id) {
        return null;
    }


}
