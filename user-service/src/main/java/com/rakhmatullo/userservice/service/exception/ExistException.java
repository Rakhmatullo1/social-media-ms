package com.rakhmatullo.userservice.service.exception;

import org.springframework.http.HttpStatus;

public class ExistException extends CustomException{
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public ExistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return STATUS;
    }
}
