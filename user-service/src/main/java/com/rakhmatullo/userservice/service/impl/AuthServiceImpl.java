package com.rakhmatullo.userservice.service.impl;

import com.rakhmatullo.userservice.integration.KeycloakClientService;
import com.rakhmatullo.userservice.properties.KeycloakProperties;
import com.rakhmatullo.userservice.service.AuthService;
import com.rakhmatullo.userservice.service.dto.LoginDTO;
import com.rakhmatullo.userservice.service.dto.LoginRequestDTO;
import com.rakhmatullo.userservice.service.dto.RegisterRequestDto;
import com.rakhmatullo.userservice.service.dto.ResponseDTO;
import com.rakhmatullo.userservice.service.exception.ExistException;
import com.rakhmatullo.userservice.service.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService  {

    private final UserMapper userMapper;
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final KeycloakClientService keycloakClientService;

    private UsersResource usersResource;

    @PostConstruct
    void init() {
        log.info("Started initialize users resource of keycloak");
        usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
    }

    @Override
    public Optional<ResponseDTO> login(LoginDTO loginDTO) {
        log.info("Requested to login {}", loginDTO);

        LoginRequestDTO loginRequestDTO = userMapper.toLoginRequestDTO(loginDTO);

        ResponseEntity<ResponseDTO> response = keycloakClientService.login(loginRequestDTO);

        log.info("Response : {}", response);
        return Optional.ofNullable(response.getBody());
    }

    @Override
    public void create(RegisterRequestDto requestDto) {
        log.info("Requested to create new User");
        UserRepresentation userRepresentation = userMapper.toUserRepresentation(requestDto);

        checkUserExistence(requestDto);

        usersResource.create(userRepresentation);
        log.info("Successfully created new user");
    }

    @Override
    public Optional<String> getUsername(UUID userId) {
        log.info("Requested to get username by userId {}", userId);
        UserRepresentation user = usersResource.get(String.valueOf(userId)).toRepresentation();

        String username = user.getUsername();
        log.info("Successfully fetched username {}", username);
        return Optional.of(username);
    }

    private void checkUserExistence(RegisterRequestDto requestDto) {
        List<UserRepresentation> users = usersResource.searchByUsername(requestDto.getUsername(), true);

        if(!users.isEmpty()) {
            log.warn("Uses already exits with the username");
            throw new ExistException("User already exists with the username: "+ requestDto.getUsername());
        }

        users = usersResource.searchByEmail(requestDto.getEmail(), true);

        if(!users.isEmpty()) {
            log.warn("Uses already exits with the email");
            throw new ExistException("User already exists with the email: "+ requestDto.getEmail());
        }
    }
}
