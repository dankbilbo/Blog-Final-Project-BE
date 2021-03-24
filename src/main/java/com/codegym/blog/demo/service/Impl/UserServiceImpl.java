package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.Entity.UserPrincipal;
import com.codegym.blog.demo.model.Entity.UserRole;
import com.codegym.blog.demo.model.Entity.UserVerificationToken;
import com.codegym.blog.demo.model.in.UserBanIn;
import com.codegym.blog.demo.model.in.UserPasswordIn;
import com.codegym.blog.demo.model.in.UserSignUp;
import com.codegym.blog.demo.model.in.UserUpdateIn;
import com.codegym.blog.demo.model.out.UserOut;
import com.codegym.blog.demo.model.out.UserPasswordOut;
import com.codegym.blog.demo.model.response.Response;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.repository.BlogRepository;
import com.codegym.blog.demo.repository.RoleRepository;
import com.codegym.blog.demo.repository.UserRepository;
import com.codegym.blog.demo.repository.UserVerificationTokenRepository;
import com.codegym.blog.demo.security.PasswordEncoder;
import com.codegym.blog.demo.service.ActionService.UserService;
import com.codegym.blog.demo.model.mapper.MapEntityAndOut;
import com.codegym.blog.demo.service.Interface.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserVerificationTokenRepository userVerificationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ResponseEntity<SystemResponse<List<UserOut>>> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        if (!isAdmin) {
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }
        List<UserOut> userOuts = MapEntityAndOut.mapListUserEntityAndOut(users);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, userOuts);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> signUp(UserSignUp userSignUp) {
        Optional<User> userFindByUsername = userRepository.findByUsername(userSignUp.getUsername());
        if (userFindByUsername.isPresent()) {
            return Response.bad_request(ErrorCodeMessage.BAD_REQUEST, StringResponse.USERNAME_EXISTED);
        }

        Optional<User> userFindByEmail = userRepository.findByEmail(userSignUp.getEmail());
        if (userFindByEmail.isPresent()) {
            return Response.bad_request(ErrorCodeMessage.BAD_REQUEST, StringResponse.EMAIL_EXISTED);
        }
        String userPassword = passwordEncoder.encoder().encode(userSignUp.getPassword());


        Optional<UserRole> roleMember = roleRepository.findByRoleName("MEMBER");
        if (!roleMember.isPresent()) {
            roleRepository.save(new UserRole("MEMBER"));
        }

        Set<UserRole> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("MEMBER").get());

        User user = userRepository.save(
                new User(userSignUp.getUsername()
                        , userPassword
                        , userSignUp.getEmail()
                        , LocalDateTime.now()
                        , roles
                ));

        String token = UUID.randomUUID().toString();

        userVerificationTokenRepository.save(
                new UserVerificationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        user
                ));

        sendVerificationEmail(token, userSignUp.getEmail());

        return Response.ok(ErrorCodeMessage.CREATED, StringResponse.REGISTERED, userSignUp.getEmail());
    }

    @Override
    public ResponseEntity<SystemResponse<UserOut>> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }

        UserOut userOut = MapEntityAndOut.mapUserEntityAndOut(user.get());

        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<UserOut>> updateUser(UserUpdateIn userUpdateIn, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser
                || isAdmin)) {
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }

        User userEntitySaved = userRepository.save(MapEntityAndOut.mapUserUpdateInAndUserEntity(userUpdateIn, user.get()));
        UserOut userOut = MapEntityAndOut.mapUserEntityAndOut(userEntitySaved);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.USER_UPDATED, userOut);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser || isAdmin)) {
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }

        blogRepository.updateBlogAfterDeleteUser(user.get().getId());
        userRepository.deleteById(id);
        return Response.no_content(ErrorCodeMessage.NO_CONTENT, StringResponse.USER_DELETED);
    }

    @Override
    public ResponseEntity<SystemResponse<UserPasswordOut>> changePassword(UserPasswordIn userPasswordIn, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        boolean correctUser = username.equals(user.get().getUsername());

        if (!(correctUser
                || isAdmin)) {
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }
        userPasswordIn.setPassword(passwordEncoder.encoder().encode(userPasswordIn.getPassword()));
        User userEntityIn = MapEntityAndOut.mapUserPasswordInAndEntity(userPasswordIn, user.get());
        userRepository.save(userEntityIn);

        UserPasswordOut userPasswordOut = MapEntityAndOut.mapUserPasswordAndOut(userEntityIn);
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, userPasswordOut);
    }

    @Override
    public ResponseEntity<SystemResponse<String>> verify(String token) {
        Optional<UserVerificationToken> verificationToken
                = userVerificationTokenRepository.findByToken(token);

        if (!verificationToken.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.TOKEN_NOT_EXISTED);
        }
        if (verificationToken.get().getVerifiedAt() != null) {
            return Response.no_content(ErrorCodeMessage.NO_CONTENT, StringResponse.USER_VERIFIED_ALREADY);
        }

        LocalDateTime expiredAt = verificationToken.get().getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return Response.no_content(ErrorCodeMessage.NO_CONTENT, StringResponse.TOKEN_EXPIRED);
        }

        userVerificationTokenRepository.verifyToken(LocalDateTime.now(), token);

        userRepository.enabledUserByEmail(verificationToken.get().getUser().getEmail());
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.VERIFY_SUCCESS, verificationToken.get().getUser().getUsername());
    }

    @Override
    public ResponseEntity<SystemResponse<String>> blockUser(UserBanIn userBanIn, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND, StringResponse.USER_NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin || user.get().getRole().stream().anyMatch(userRole -> userRole.getRoleName().equals("ADMIN"))) {
            return Response.forbidden(ErrorCodeMessage.FORBIDDEN, StringResponse.FORBIDDEN);
        }

        user.get().setLocked(true);
        userRepository.save(user.get());
        return Response.ok(ErrorCodeMessage.SUCCESS, StringResponse.OK, StringResponse.BANNED + ' ' + user.get().getUsername());
    }

    private void sendVerificationEmail(String token, String email) {
        String linkVerify = "http://localhost:8080/register/" + token;
        String content = "please verify your account by clicking this link " + linkVerify;
        String topic = "Pro Hub verify account";
        emailService.sendEmail(email, content, topic);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        return UserPrincipal.build(user);
    }
}
