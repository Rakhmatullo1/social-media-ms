package com.rakhmatullo.notificationservice.repository;

import com.rakhmatullo.notificationservice.entity.Notification;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotificationRepository extends R2dbcRepository<Notification, Long> {

    Flux<Notification> getNotificationByToUserId(String userId);
}
