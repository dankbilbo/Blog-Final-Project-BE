package com.codegym.blog.demo.security;


import com.codegym.blog.demo.model.Entity.JwtResponse;
import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.model.Entity.UserPrincipal;
import com.codegym.blog.demo.model.in.UserLogin;
import com.codegym.blog.demo.repository.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Component
@Service
public class JwtService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    private static final String SECRET_KEY = "123456789123456789123456789123456789123456789";
    private static final long EXPIRE_KEY = 123456789L;
    public static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    public ResponseEntity<?> login(UserLogin userLogin){
        Optional<User> user = userRepository.findByUsername(userLogin.getUsername());
        if (!user.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("username not found");
        }
        String passwordDb = user.get().getPassword();
        if (!passwordEncoder.encoder().matches(userLogin.getPassword(),passwordDb)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password incorrect");
        };

        UserPrincipal userPrincipal = UserPrincipal.build(user.get());

        if (!userPrincipal.isEnabled()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("please verify your account at " + user.get().getEmail());
        }
        if (!userPrincipal.isAccountNonLocked()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("your account has been banned");
        };

        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, userLogin.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = generateAccessToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    public String generateAccessToken(Authentication authentication) {
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRE_KEY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}