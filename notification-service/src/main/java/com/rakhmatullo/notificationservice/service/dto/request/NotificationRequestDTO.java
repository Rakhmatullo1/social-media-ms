package com.rakhmatullo.notificationservice.service.dto.request;

import com.rakhmatullo.notificationservice.entity.enums.NotificationType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotificationRequestDTO {
    private NotificationType type;
    private String userId;
    private String postId;
    private String toUserId;
}
