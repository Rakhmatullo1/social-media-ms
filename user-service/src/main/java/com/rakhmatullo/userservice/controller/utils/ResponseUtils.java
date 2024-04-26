package com.rakhmatullo.userservice.controller.utils;

import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

public class ResponseUtils {

    public static <T> ResponseEntity<T> wrap(Optional<T> response ) {
        if (Objects.isNull(response)) {
            throw new RuntimeException("Invalid argument is passed! Optional must not be null!");
        }

        return  response.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
