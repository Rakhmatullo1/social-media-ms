package com.rakhmatullo.postsservice.service.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    public static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return  STATUS;
    }
}
