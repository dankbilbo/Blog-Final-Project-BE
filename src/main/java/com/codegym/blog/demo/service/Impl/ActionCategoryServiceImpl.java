package com.codegym.blog.demo.service.Impl;

import com.codegym.blog.demo.Keywords.ErrorCodeMessage;
import com.codegym.blog.demo.Keywords.StringResponse;
import com.codegym.blog.demo.model.Entity.Category;
import com.codegym.blog.demo.model.response.Response;
import com.codegym.blog.demo.model.response.SystemResponse;
import com.codegym.blog.demo.service.ActionService.CategoryActionService;
import com.codegym.blog.demo.service.Interface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ActionCategoryServiceImpl implements CategoryActionService {

    @Autowired
    public CategoryService categoryService;

    @Override
    public ResponseEntity<SystemResponse<List<Category>>> categoryList() {
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty()) {
            return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_FOUND);
        }
        return Response.ok(ErrorCodeMessage.SUCCESS
                , StringResponse.OK
                , categories);
    }

    @Override
    public ResponseEntity<SystemResponse<Category>> createCategory(Category category) {
        List<Category> categories = categoryService.findAll();
//        categories.stream().anyMatch(category1->category1.getName().equals(category.getName()));
        for (Category c : categories) {
            if (c.getName().equals(category.getName())) {
                return Response.bad_request(ErrorCodeMessage.BAD_REQUEST,StringResponse.CATEGORY_EXISTED);
            }
            categoryService.save(category);
        }
        return Response.ok(ErrorCodeMessage.SUCCESS
                , StringResponse.OK
                , category);
    }

    @Override
    public ResponseEntity<SystemResponse<Category>> findById(Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return Response.ok(ErrorCodeMessage.SUCCESS
                    , StringResponse.OK
                    , category.get());
        }
        return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_FOUND);
    }

    @Override
    public ResponseEntity<SystemResponse<Category>> updateById(Long id, Category category) {
        Category categoryCurrent = categoryService.findById(id).get();
        if (categoryCurrent != null) {
            category.setId(id);
            categoryService.save(category);
            return Response.ok(ErrorCodeMessage.SUCCESS
                    , StringResponse.OK
                    , categoryCurrent);
        }
        return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_FOUND);
    }

    @Override
    public ResponseEntity<SystemResponse<Category>> deleteById(Long id) {
        Category category = categoryService.findById(id).get();
        if (category != null) {
            categoryService.deleteById(id);
            return Response.ok(ErrorCodeMessage.SUCCESS
                    , StringResponse.OK
                    , category);
        }
        return Response.not_found(ErrorCodeMessage.NOT_FOUND,StringResponse.NOT_FOUND);
    }
}
