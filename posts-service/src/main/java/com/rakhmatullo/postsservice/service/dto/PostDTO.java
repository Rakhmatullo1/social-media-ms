package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDTO {
    private UUID id;
    private String description;
    private String createdAt;
    private boolean isPrivate;
    private String userPhotoUrl;
    private String username;
    private int likesCount;
}
