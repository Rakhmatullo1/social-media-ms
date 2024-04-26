package com.rakhmatullo.postsservice.controller.utils;

import com.rakhmatullo.postsservice.service.exceptions.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResponseUtils {

    private static final String TOTAL_COUNT_HEADER_NAME = "X-Total-Count";

    public static <T> ResponseEntity<T> wrapResponse(Optional<T> object, HttpStatus status) {
        if(object.isEmpty()) {
            throw new CustomException("Invalid argument is passed! Optional must not be null!");
        }

        return object.map((r)->ResponseEntity.status(status).body(r))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<T> wrapResponse(Optional<T> object) {
        if(object.isEmpty()) {
            throw new CustomException("Invalid argument is passed! Optional must not be null!");
        }

        return object.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    public static <T> ResponseEntity<List<T>> wrap(Page<T> pagination) {
        if(Objects.isNull(pagination)) {
            throw new CustomException("Invalid argument is passed! Page must not be null!");
        }

        return ResponseEntity.ok()
                .header(TOTAL_COUNT_HEADER_NAME, String.valueOf(pagination.getTotalElements()))
                .body(pagination.getContent());
    }
}
