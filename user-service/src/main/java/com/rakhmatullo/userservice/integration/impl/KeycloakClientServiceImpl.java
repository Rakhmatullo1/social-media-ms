package com.rakhmatullo.userservice.integration.impl;

import com.rakhmatullo.userservice.integration.KeycloakClientService;
import com.rakhmatullo.userservice.service.dto.LoginRequestDTO;
import com.rakhmatullo.userservice.service.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakClientServiceImpl implements KeycloakClientService {

    private static  final String LOGIN_URL ="http://localhost:9080/realms/dev/protocol/openid-connect/token";

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        log.debug("REST request to keycloak to login with {}", loginRequestDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> httpEntity = new HttpEntity<>(loginRequestDTO.toString(), httpHeaders);
        ResponseEntity<ResponseDTO> responseDTO = restTemplate.exchange(LOGIN_URL, HttpMethod.POST, httpEntity, ResponseDTO.class );
        log.debug("Successfully fetched response");
        return responseDTO;
    }
}
