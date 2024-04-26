package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private String username;
    private boolean isLiked;
    private String userPhotoUrl;
}
