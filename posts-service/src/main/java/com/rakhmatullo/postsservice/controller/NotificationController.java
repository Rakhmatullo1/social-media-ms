package com.rakhmatullo.postsservice.controller;

import com.rakhmatullo.postsservice.controller.utils.ResponseUtils;
import com.rakhmatullo.postsservice.service.dto.NotificationDTO;
import com.rakhmatullo.postsservice.service.integration.NotificationClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class NotificationController {

    private final NotificationClientService notificationClientService;

    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseEntity<Flux<NotificationDTO>> fetchAllNotifications() {
        log.debug("REST request to get all notifications");
        Optional<Flux<NotificationDTO>> notificationDTOFlux = notificationClientService.getAll();

        ResponseEntity<Flux<NotificationDTO>> response = ResponseUtils.wrapResponse(notificationDTOFlux);
        log.debug("Response: {}", response);
        return response;
    }
}
