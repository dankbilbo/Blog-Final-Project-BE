package com.codegym.blog.demo.model;

import lombok.Data;

@Data
public class SystemResponse<T> {
    private int status;
    private String message;
    private T data;

    public SystemResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public SystemResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
