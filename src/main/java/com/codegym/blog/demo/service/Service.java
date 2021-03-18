package com.codegym.blog.demo.service;

import java.util.List;
import java.util.Optional;

public interface Service <T>{
    List<T> findAll();
    Optional<T> findById(Long id);
    Optional<T> findByName(String name);
    T save(T t);
    void deleteById(Long id);
}
