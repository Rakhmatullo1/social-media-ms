package com.rakhmatullo.postsservice.service.dto;

import com.rakhmatullo.postsservice.service.enums.NotificationType;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequestDTO {
    private NotificationType type;
    private UUID userId;
    private UUID postId;
    private UUID toUserId;
}
