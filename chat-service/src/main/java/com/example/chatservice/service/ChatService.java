package com.example.chatservice.service;

import com.example.chatservice.service.dto.ChatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ChatService {
    Optional<ChatDTO> create(UUID receiverId);

    Page<ChatDTO> findAll(Pageable pageable);
}
