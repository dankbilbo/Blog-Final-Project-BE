package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.Entity.UserRole;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserSignUp;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.Response;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.repository.RoleRepository;
import com.codegym.blog.demo.repository.UserRepository;
import com.codegym.blog.demo.security.PasswordEncoder;
import com.codegym.blog.demo.service.ActionService.UserActionService;
import com.codegym.blog.demo.model.mapper.MapEntityAndOut;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<SystemResponse<UserOut>> signUp(UserSignUp userSignUp) {
        Optional<User> userFindByUsername = userRepository.findByUsername(userSignUp.getUsername());
        if (userFindByUsername.isPresent()){
            return Response.bad_request(ErrorCodeMessage.BAD_REQUEST,StringResponse.USERNAME_EXISTED);
        }

        Optional<User> userFindByEmail = userRepository.findByEmail(userSignUp.getEmail());
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

        User user = userRepository.save(
                new User(userSignUp.getUsername()
                        ,userPassword
                        ,userSignUp.getEmail()
                        , LocalDateTime.now()
                        , roles
                        ));

        UserOut userOut = MapEntityAndOut.mapUserEntityAndOut(user);

        return Response.ok(ErrorCodeMessage.CREATED
                ,StringResponse.REGISTERED
                ,userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<UserOut>> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.USER_NOT_FOUND);
        }

        UserOut userOut = MapEntityAndOut.mapUserEntityAndOut(user.get());

        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<UserOut>> updateUser(UserUpdateIn userUpdateIn, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.USER_NOT_FOUND);
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser
                || isAdmin)) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }

        User userEntitySaved = userRepository.save(MapEntityAndOut.mapUserUpdateInAndUserEntity(userUpdateIn,user.get()));

        UserOut userOut = MapEntityAndOut.mapUserEntityAndOut(userEntitySaved);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.USER_UPDATED,userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.USER_NOT_FOUND);
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser
                || isAdmin)) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }

        userRepository.deleteById(id);

        return Response.no_content(ErrorCodeMessage.NO_CONTENT,StringResponse.USER_DELETED);
    }

    @Override
    public ResponseEntity<SystemResponse<UserPasswordOut>> changePassword(UserPasswordIn userPasswordIn, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.USER_NOT_FOUND);
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser
                || isAdmin)) {
            return Response.not_authorized(ErrorCodeMessage.NOT_AUTHORIZED, StringResponse.NOT_AUTHORIZED);
        }
        User userEntityIn = MapEntityAndOut.mapUserPasswordInAndEntity(userPasswordIn,user.get());
        userRepository.save(userEntityIn);

        UserPasswordOut userPasswordOut = MapEntityAndOut.mapUserPasswordAndOut(userEntityIn);
        return Response.ok(ErrorCodeMessage.SUCCESS,StringResponse.OK,userPasswordOut);
    }


}
