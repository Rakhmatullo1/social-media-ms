package com.rakhmatullo.notificationservice.service.mapper;

import com.rakhmatullo.notificationservice.entity.Notification;
import com.rakhmatullo.notificationservice.entity.enums.NotificationType;
import com.rakhmatullo.notificationservice.service.dto.NotificationDTO;
import com.rakhmatullo.notificationservice.service.dto.request.NotificationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Date;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "read", constant = "false", resultType = Boolean.class)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(setCreatedTime())")
    Notification toNotification(NotificationRequestDTO notificationRequestDTO);


    @Mapping(target = "message", expression = "java(getMessage(notification.getType(), username))")
    NotificationDTO toNotificationDTO(Notification notification, String username);

    default String getMessage(NotificationType type, String username) {
        return String.format(type.getMessage(), username);
    }

    default String setCreatedTime() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
