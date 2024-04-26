package com.rakhmatullo.userservice.config;

import com.rakhmatullo.userservice.properties.KeycloakProperties;
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
        log.info("Starting configure keycloak");
        return KeycloakBuilder
                .builder()
                .serverUrl(properties.getServerUrl())
                .clientSecret(properties.getClientSecret())
                .clientId(properties.getClientId())
                .password(properties.getPassword())
                .username(properties.getUsername())
                .grantType(OAuth2Constants.PASSWORD)
                .realm(properties.getRealm())
                .scope(properties.getScope())
                .build();
    }
}
