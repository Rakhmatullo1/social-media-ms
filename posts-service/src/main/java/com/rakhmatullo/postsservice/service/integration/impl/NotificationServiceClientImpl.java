package com.rakhmatullo.postsservice.service.integration.impl;

import com.rakhmatullo.postsservice.security.SecurityUtils;
import com.rakhmatullo.postsservice.service.dto.NotificationDTO;
import com.rakhmatullo.postsservice.service.dto.NotificationRequestDTO;
import com.rakhmatullo.postsservice.service.integration.NotificationClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceClientImpl implements NotificationClientService {

    private final WebClient client;
    private static final String HTTP_LOCALHOST_8084_API = "http://localhost:8084/api/notification";

    @Override
    public Optional<Mono<NotificationDTO>> create(NotificationRequestDTO notificationRequestDTO) {
        log.info("REST request to create notification to Notification Service");
        Mono<NotificationDTO> response = client.post()
                .uri(HTTP_LOCALHOST_8084_API )
                .bodyValue(notificationRequestDTO)
                .retrieve()
                .bodyToMono(NotificationDTO.class);

        log.info("Successfully exchanged");
        log.info("Response: {}", response);
        return Optional.of(response);
    }

    @Override
    public Optional<Flux<NotificationDTO>> getAll() {
        log.info("REST request to get notifications from Notification Service");
        UUID toUserId = SecurityUtils.getCurrentUserId();
        Flux<NotificationDTO> response = client.get()
                .uri(HTTP_LOCALHOST_8084_API+"?toUserId="+toUserId)
                .retrieve()
                .bodyToFlux(NotificationDTO.class);

        log.info("Successfully fetched");
        return Optional.of(response);
    }
}
