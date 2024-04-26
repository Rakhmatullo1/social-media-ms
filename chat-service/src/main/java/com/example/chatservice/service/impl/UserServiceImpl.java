package com.example.chatservice.service.impl;

import com.example.chatservice.properties.KeycloakProperties;
import com.example.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final KeycloakProperties keycloakProperties;
    private final Keycloak keycloak;

    @Override
    public Optional<UserRepresentation> getUserInfo(UUID id) {
        log.debug("Requested to get user info {}", id);
        UserResource userResource =
                keycloak.realm(keycloakProperties.getRealm())
                        .users().get(String.valueOf(id));

        UserRepresentation userRepresentation = userResource.toRepresentation();
        log.info("Successfully fetched user info");
        return Optional.of(userRepresentation);
    }
}
