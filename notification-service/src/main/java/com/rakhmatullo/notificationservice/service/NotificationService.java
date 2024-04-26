package com.rakhmatullo.notificationservice.service;

import com.rakhmatullo.notificationservice.service.dto.NotificationDTO;
import com.rakhmatullo.notificationservice.service.dto.request.NotificationRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface NotificationService {
    Mono<NotificationDTO> create(NotificationRequestDTO requestDTO);

    Flux<NotificationDTO> getAll(String toUserId);
}
