package com.example.chatservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageDTO {
    private UUID id;
    private String senderName;
    private String message;
    private String createdAt;
    private String senderPhotoUrl;
    private boolean isRead;
}
