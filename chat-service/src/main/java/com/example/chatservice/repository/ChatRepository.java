package com.example.chatservice.repository;

import com.example.chatservice.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Optional<Chat> findByCreatedBy(UUID id);

    Page<Chat> findAllByCreatedBy(UUID userId, Pageable pageable );
}
