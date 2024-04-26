package com.example.chatservice.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
