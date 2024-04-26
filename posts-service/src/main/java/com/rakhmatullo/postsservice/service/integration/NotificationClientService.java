package com.rakhmatullo.postsservice.service.integration;

import com.rakhmatullo.postsservice.service.dto.NotificationDTO;
import com.rakhmatullo.postsservice.service.dto.NotificationRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface NotificationClientService {
    Optional<Mono<NotificationDTO>> create(NotificationRequestDTO notificationRequestDTO);

    Optional<Flux<NotificationDTO>> getAll();
}
