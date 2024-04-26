package com.rakhmatullo.userservice.integration;

import com.rakhmatullo.userservice.service.dto.LoginRequestDTO;
import com.rakhmatullo.userservice.service.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface KeycloakClientService {
    ResponseEntity<ResponseDTO> login(LoginRequestDTO loginRequestDTO);
}
