package com.rakhmatullo.notificationservice.entity.enums;

import lombok.Getter;

@Getter
public enum NotificationType {
    NEW_POST("%s user created new post"),
    LIKED("% user liked your post"),
    NEW_COMMENT("%s user commented on your post"),
    NEW_MESSAGE("new message from user %s");

    private final String message;

    NotificationType(String message) {
        this.message = message;
    }
}
