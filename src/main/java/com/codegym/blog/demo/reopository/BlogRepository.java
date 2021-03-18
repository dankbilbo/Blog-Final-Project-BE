package com.codegym.blog.demo.reopository;

import com.codegym.blog.demo.model.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
}
