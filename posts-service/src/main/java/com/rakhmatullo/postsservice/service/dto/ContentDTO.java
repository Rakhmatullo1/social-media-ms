package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContentDTO {
    private UUID id;
    private boolean isMain;
    private String createdAt;
}
