package com.rakhmatullo.userservice.service;


import com.rakhmatullo.userservice.service.dto.LoginDTO;
import com.rakhmatullo.userservice.service.dto.RegisterRequestDto;
import com.rakhmatullo.userservice.service.dto.ResponseDTO;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {
    Optional<ResponseDTO> login(LoginDTO loginDTO);

    void create(RegisterRequestDto requestDto);

    Optional<String> getUsername(UUID userId);
}
