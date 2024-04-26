package com.example.chatservice.config;

import com.example.chatservice.properties.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KeycloakConfiguration {

    private final KeycloakProperties properties;

    @Bean
    public Keycloak configure() {
        log.debug("Starting configure Keycloak");
        return KeycloakBuilder
                .builder()
                .serverUrl(properties.getServerUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .realm(properties.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .scope(properties.getScope())
                .build();
    }
}
