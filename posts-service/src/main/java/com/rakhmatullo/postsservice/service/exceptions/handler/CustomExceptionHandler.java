package com.rakhmatullo.postsservice.service.exceptions.handler;

import com.rakhmatullo.postsservice.controller.utils.ResponseUtils;
import com.rakhmatullo.postsservice.service.dto.ErrorDTO;
import com.rakhmatullo.postsservice.service.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handle(CustomException customException) {
        Optional<ErrorDTO> error = createErrorBody(customException);
        ResponseEntity<ErrorDTO> response = ResponseUtils.wrapResponse(error, customException.getStatus());
        log.error("Error {}", response);
        return response;
    }

    private Optional<ErrorDTO> createErrorBody(CustomException customException) {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setError(customException.getMessage());
        errorDTO.setStatus(customException.getStatus().value());
        errorDTO.setTimestamp(System.currentTimeMillis());

        return Optional.of(errorDTO);
    }
}
