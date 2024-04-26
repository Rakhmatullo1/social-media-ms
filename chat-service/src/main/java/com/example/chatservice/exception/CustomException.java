package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
