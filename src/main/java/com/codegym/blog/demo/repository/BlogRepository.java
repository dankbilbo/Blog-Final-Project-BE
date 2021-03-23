package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByPrivacyIsTrue();

    List<Blog> findAllByUser_Username(String username);
    List<Blog> findAllByUser_UsernameAndTitleContaining(String username,String searchKey);

    @Query(nativeQuery = true,
            value ="SELECT * FROM blog WHERE title like ?1 OR short_description like ?1")
    List<Blog> search(String searchKey);
}
