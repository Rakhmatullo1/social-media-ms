package com.rakhmatullo.postsservice.service.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }
}
