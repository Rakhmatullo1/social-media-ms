package com.rakhmatullo.postsservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("keycloak")
@Data
public class KeycloakProperties {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String username;
    private String password;
    private String serverUrl;
    private String realm;
}
