package com.codegym.blog.demo.repository;

import com.codegym.blog.demo.model.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBlog_Id(Long id);
    List<Comment> findAllByRepliedTo_Id(Long id);

}
