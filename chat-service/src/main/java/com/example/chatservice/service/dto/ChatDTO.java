package com.example.chatservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatDTO {
    private UUID id;
    private String receiverName;
    private String receiverPhotoUrl;
    private String lastMessage;
    private String messageReceivedTime;
}
