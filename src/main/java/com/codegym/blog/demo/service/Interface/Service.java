package com.codegym.blog.demo.service.Interface;

import java.util.List;
import java.util.Optional;

public interface Service <T>{
    List<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    void deleteById(Long id);
}
