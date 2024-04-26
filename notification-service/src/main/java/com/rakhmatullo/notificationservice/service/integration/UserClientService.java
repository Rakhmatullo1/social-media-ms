package com.rakhmatullo.notificationservice.service.integration;

import java.util.Optional;
import java.util.UUID;

public interface UserClientService {
    Optional<String> getUsername(UUID id);
}
