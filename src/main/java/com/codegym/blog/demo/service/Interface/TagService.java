package com.codegym.blog.demo.service.Interface;

import com.codegym.blog.demo.model.Entity.Tag;

import java.util.Optional;

public interface TagService extends Service<Tag>{
    Optional<Tag> findByName(String name);
}
