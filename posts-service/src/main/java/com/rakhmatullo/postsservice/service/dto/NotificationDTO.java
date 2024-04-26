package com.rakhmatullo.postsservice.service.dto;

import com.rakhmatullo.postsservice.service.enums.NotificationType;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationDTO {
    private Long id;
    private NotificationType type;
    private String message;
    private String createdAt;
    private UUID postId;
    private boolean isRead;
}
