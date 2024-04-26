package com.rakhmatullo.userservice.service.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private String message;
    private int status;
    private long timestamp;
}
