package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByRoleName(String roleName);
}
