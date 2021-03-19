package com.codegym.blog.demo.reopository;

import com.codegym.blog.demo.model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
