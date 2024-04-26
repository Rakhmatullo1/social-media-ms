package com.rakhmatullo.notificationservice.controller;

import com.rakhmatullo.notificationservice.service.NotificationService;
import com.rakhmatullo.notificationservice.service.dto.NotificationDTO;
import com.rakhmatullo.notificationservice.service.dto.request.NotificationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value =  "/notification", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Mono<NotificationDTO>> create(@RequestBody NotificationRequestDTO requestDTO) {
        return ResponseEntity.ok(notificationService.create(requestDTO));
    }

    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<NotificationDTO>> getAllByToUserId(@RequestParam("toUserId") String toUserId) {
        return ResponseEntity.ok(notificationService.getAll(toUserId));
    }
}
