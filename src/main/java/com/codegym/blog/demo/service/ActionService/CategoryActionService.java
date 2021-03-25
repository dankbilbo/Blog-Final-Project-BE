package com.codegym.blog.demo.service.ActionService;

import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.response.SystemResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryActionService {

    ResponseEntity<SystemResponse<List<Category>>> categoryList();

    ResponseEntity<SystemResponse<Category>> createCategory(Category category);

    ResponseEntity<SystemResponse<Category>> findById(Long id);

    ResponseEntity<SystemResponse<Category>> updateById(Long id, Category category);

    ResponseEntity<SystemResponse<Category>> deleteById(Long id);

    ResponseEntity<SystemResponse<List<Category>>> getTopFives();
}
