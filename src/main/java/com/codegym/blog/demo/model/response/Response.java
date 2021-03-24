package com.codegym.blog.demo.model.response;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class Response{
    public static <T> ResponseEntity<SystemResponse<T>> internalServerError(int errorCodeResponse,String message){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> ok(int errorCodeResponse, String messageResponse, T t){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,messageResponse,t));
    }

    public static <T> ResponseEntity<SystemResponse<T>> bad_request(int errorCodeResponse,String message){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> not_found(int errorCodeResponse,String message){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> not_authorized(int errorCodeResponse,String message){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }

    public static <T> ResponseEntity<SystemResponse<T>> created(int errorCodeResponse,String message,T t){
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message,t));
    }

    public static ResponseEntity<SystemResponse<String>> no_content(int errorCodeResponse,String message) {
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }

    public static <T>  ResponseEntity<SystemResponse<T>> forbidden(int errorCodeResponse,String message) {
        return ResponseEntity
                .status(errorCodeResponse)
                .body(new SystemResponse<>(errorCodeResponse,message));
    }
}
