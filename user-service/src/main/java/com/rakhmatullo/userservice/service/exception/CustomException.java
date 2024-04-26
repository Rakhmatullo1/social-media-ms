package com.rakhmatullo.userservice.service.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return STATUS;
    }
}
