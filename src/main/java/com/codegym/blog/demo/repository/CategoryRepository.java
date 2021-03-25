package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(nativeQuery = true,
    value = "select category.id,category.name,category.created_at" +
            " from category" +
            " join blog on blog.category_id = category.id" +
            " group by category.id order by count(category.id) desc limit 5")
    List<Category> find5MostBlogsCategory();

    @Query(nativeQuery = true,
    value = "select count(*) from blog where blog.category_id = ?1")
    Long countBlogsByCategoryId(Long id);
}
