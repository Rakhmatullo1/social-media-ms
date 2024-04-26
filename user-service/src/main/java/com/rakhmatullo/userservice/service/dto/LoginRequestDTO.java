package com.rakhmatullo.userservice.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.keycloak.OAuth2Constants;

@Data
@Accessors(chain = true)
public class LoginRequestDTO {

    private String clientId="demo";
    private String username ;
    private String password;
    private String grantType = OAuth2Constants.PASSWORD;
    private String clientSecret="demo";
    private String scope="openid";

    @Override
    public String toString() {
        return "client_id=" + clientId  +
                "&username=" + username  +
                "&password=" + password+
                "&grant_type=" + grantType +
                "&client_secret=" + clientSecret +
                "&scope=" + scope;

    }
}
