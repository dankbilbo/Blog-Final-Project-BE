package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken,Long> {

    Optional<UserVerificationToken> findByToken(String token);

    @Query(nativeQuery = true,
            value = "UPDATE user_verification_token " +
                    "SET verified_at = ?1 " +
                    "WHERE token = ?2")
    @Modifying
    void verifyToken(LocalDateTime verifyTime,String token);
}
