package com.example.chatservice.service.impl;

import com.example.chatservice.entity.Chat;
import com.example.chatservice.repository.ChatRepository;
import com.example.chatservice.security.SecurityUtils;
import com.example.chatservice.service.ChatService;
import com.example.chatservice.service.dto.ChatDTO;
import com.example.chatservice.service.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public Optional<ChatDTO> create(UUID receiverId) {
        log.info("Requested to create new chat");
        Optional<Chat> chatOptional = chatRepository.findByCreatedBy(receiverId);
        Chat chat = buildChat(receiverId);

        if(chatOptional.isPresent()) {
            chat = chatOptional.get();
        }

        ChatDTO chatDTO = chatMapper.toChatDTO(chat);
        log.info("Successfully created");
        return Optional.of(chatDTO);
    }

    @Override
    public Page<ChatDTO> findAll(Pageable pageable) {
        log.info("Successfully get all chats");

        UUID userId = SecurityUtils.getCurrentUserId();
        Page<ChatDTO> chats = chatRepository.findAllByCreatedBy(userId, pageable).map(chatMapper::toChatDTO);

        log.info("Successfully fetched");
        return chats;
    }

    private Chat buildChat(UUID receiverId) {
        UUID userId = SecurityUtils.getCurrentUserId();

        Chat chat = new Chat();
        chat.setReceiverId(receiverId);
        chat.setCreatedBy(userId);
        chat.setMessages(new ArrayList<>());

        return chatRepository.save(chat);
    }
}
