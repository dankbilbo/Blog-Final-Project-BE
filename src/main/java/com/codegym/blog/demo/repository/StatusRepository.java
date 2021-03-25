package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    Optional<Status> findAllByUser_IdAndBlog_Id(Long userId, Long blogId);

    List<Status> findAllByBlog_Id(Long blogId);

}
