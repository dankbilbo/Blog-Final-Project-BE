//package com.codegym.blog.demo.service.Impl;
//
//import com.codegym.blog.demo.model.Entity.Blog;
//import com.codegym.blog.demo.repository.BlogRepository;
//import com.codegym.blog.demo.service.Interface.BlogService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class BlogServiceImpl implements BlogService {
//
//    @Autowired
//    private final BlogRepository blogRepository;
//
//    @Override
//    public List<Blog> findAll() {
//        return blogRepository.findAll();
//    }
//
//    @Override
//    public Optional<Blog> findById(Long id) {
//        return blogRepository.findById(id);
//    }
//
//    @Override
//    public Blog save(Blog blog) {
//        return blogRepository.save(blog);
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        blogRepository.deleteById(id);
//    }
//
//
//    @Override
//    public List<Blog> findALlPublicBlogs() {
//        return blogRepository.findAllByPrivacyIsTrue();
//    }
//
//    @Override
//    public List<Blog> findAllByUsername(String username) {
//        return blogRepository.findAllByUser_Username(username);
//    }
//}
