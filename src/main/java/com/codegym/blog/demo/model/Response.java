package com.codegym.blog.demo.model;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class Response{
    public static <T> ResponseEntity<SystemResponse<T>> internalServerError(String message){
        return ResponseEntity
                .status(500)
                .body(new SystemResponse<>(500,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(int errorCodeResponse, String messageResponse, T t){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,messageResponse,t));
    }

    public static <T> ResponseEntity<SystemResponse<T>> bad_request(String message){
        return ResponseEntity
                .status(400)
                .body(new SystemResponse<>(400,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> not_found(String message){
        return ResponseEntity
                .status(404)
                .body(new SystemResponse<>(404,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> not_authorized(String message){
        return ResponseEntity
                .status(403)
                .body(new SystemResponse<>(403,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> created(String message){
        return ResponseEntity
                .status(201)
                .body(new SystemResponse<>(201,message));
    }

    public static ResponseEntity<SystemResponse<String>> no_content(String message) {
        return ResponseEntity
                .status(204)
                .body(new SystemResponse<>(204,message));
    }
}
