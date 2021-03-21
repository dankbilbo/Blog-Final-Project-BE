package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,value = "UPDATE user SET enabled = TRUE WHERE email = ?1")
    @Modifying
    void enabledUserByEmail(String email);

}
