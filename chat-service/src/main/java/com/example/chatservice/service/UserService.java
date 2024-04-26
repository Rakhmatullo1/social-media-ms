package com.example.chatservice.service;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserRepresentation> getUserInfo(UUID id);
}
