package com.rakhmatullo.notificationservice.service.integration.impl;

import com.rakhmatullo.notificationservice.service.integration.UserClientService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClientServiceImpl implements UserClientService {

    private final static String HTTP_URL_8080 = "http://localhost:8080/api/user";

    private final WebClient webClient;

    @Override
    public Optional<String> getUsername(UUID id) {
        log.debug("REST request to get username from user service");
        Mono<String> response = webClient.get().uri(HTTP_URL_8080+"/"+id).retrieve().bodyToMono(String.class);

        String username = response.block();

        if(StringUtils.isBlank(username)) {
            log.warn("Response is null");
            return Optional.of("Hello world");
        }

        log.debug("Response {}", username);
        return Optional.of(username);
    }
}
