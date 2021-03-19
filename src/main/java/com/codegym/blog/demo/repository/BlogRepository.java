package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByStatusIsTrue();
}
