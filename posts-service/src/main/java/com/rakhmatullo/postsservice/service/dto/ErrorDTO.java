package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private String error;
    private int status;
    private long timestamp;
}
