package com.rakhmatullo.postsservice.service.impl;

import com.rakhmatullo.postsservice.properties.KeycloakProperties;
import com.rakhmatullo.postsservice.service.UserService;
import com.rakhmatullo.postsservice.service.dto.UserDTO;
import com.rakhmatullo.postsservice.service.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final UserMapper userMapper;
    private UsersResource usersResource;

    @PostConstruct
    void init() {
        usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
    }

    @Override
    public UserDTO getUserInfo(UUID id) {
        log.info("Requested to get info");
        UserResource userResource = usersResource.get(String.valueOf(id));

        UserDTO userDTO = userMapper.toUserDTO(userResource.toRepresentation());
        log.info("Successfully fetched user info");
        return userDTO;
    }

    @Override
    public List<UUID> getAll() {
        log.info("Requested to fetch all users ID");
        return usersResource
                .list().stream()
                .filter(u-> !Objects.equals(u.getUsername(), keycloakProperties.getUsername()))
                .map(u->UUID.fromString(u.getId()))
                .collect(Collectors.toList());
    }
}
