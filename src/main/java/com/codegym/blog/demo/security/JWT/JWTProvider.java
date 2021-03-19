package com.codegym.blog.demo.security.JWT;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JWTProvider {
    @Value("${ProHub}")
    private String jwtSecret;

    @Value("${jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration*100))
                .signWith(SignatureAlgorithm.HS256,jwtSecret)
                .compact();
    }

//    public boolean validateJWTToken(String authencicationToken){
//        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authencicationToken);
//        return
//    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJwt(token)
                .getBody().getSubject();
    }
}
