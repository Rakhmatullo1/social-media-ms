package com.rakhmatullo.notificationservice.service.impl;

import com.rakhmatullo.notificationservice.entity.Notification;
import com.rakhmatullo.notificationservice.repository.NotificationRepository;
import com.rakhmatullo.notificationservice.service.NotificationService;
import com.rakhmatullo.notificationservice.service.dto.NotificationDTO;
import com.rakhmatullo.notificationservice.service.dto.request.NotificationRequestDTO;
import com.rakhmatullo.notificationservice.service.integration.UserClientService;
import com.rakhmatullo.notificationservice.service.mapper.NotificationMapper;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final UserClientService userService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    @Override
    public Mono<NotificationDTO> create(NotificationRequestDTO requestDTO) {
        log.info("Requested to create new notification {}", requestDTO);
        Notification notification = notificationMapper.toNotification(requestDTO);

        Mono<NotificationDTO> theNotification = notificationRepository.save(notification).map(n->notificationMapper.toNotificationDTO(n, "argus"));
        log.info("Successfully");
        return theNotification;
    }

    @Override
    public Flux<NotificationDTO> getAll(String toUserId) {
        log.info("Requested to get own notifications");

        Flux<NotificationDTO> notifications = notificationRepository.getNotificationByToUserId(toUserId).map(n->notificationMapper.toNotificationDTO(n,"argus"));

        log.info("Successfully fetched data");
        return notifications;
    }

    private String getUsername(String id) {
        return userService.getUsername(UUID.fromString(id)).orElseThrow(()->new NotFoundException("Username is not found"));
    }
}
