package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String message;
    private String username;
    private String userPhotoUrl;
    private int likesCount;
    private String createdAt;
}
