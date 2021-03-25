package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "UPDATE user SET enabled = TRUE WHERE email = ?1")
    @Modifying
    void enabledUserByEmail(String email);


    @Query(nativeQuery = true,value = "select user.id,user.username,user.avatarurl,user.created_at,user.email,user.first_name,user.last_name,user.locked,user.enabled,user.password" +
            " from user " +
            " join user_verification_token on user.id = user_verification_token.user_id" +
            " where user_verification_token.token = :token")
    Optional<User> findUserByTokentoken(@Param("token") String token);

}
