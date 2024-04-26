package com.rakhmatullo.userservice.service.exception.handler;

import com.rakhmatullo.userservice.service.dto.ErrorDTO;
import com.rakhmatullo.userservice.service.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handler(CustomException ex) {
        return new ResponseEntity<>(createErrorMessage(ex), ex.getStatusCode());
    }

    private ErrorDTO createErrorMessage(CustomException ex) {
        ErrorDTO error = new ErrorDTO();

        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        error.setStatus(ex.getStatusCode().value());

        return  error;
    }
}
