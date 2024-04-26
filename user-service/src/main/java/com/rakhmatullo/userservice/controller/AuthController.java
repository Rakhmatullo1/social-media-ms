package com.rakhmatullo.userservice.controller;

import com.rakhmatullo.userservice.controller.utils.ResponseUtils;
import com.rakhmatullo.userservice.service.AuthService;
import com.rakhmatullo.userservice.service.dto.LoginDTO;
import com.rakhmatullo.userservice.service.dto.RegisterRequestDto;
import com.rakhmatullo.userservice.service.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO){
        log.debug("REST request to login user");
        Optional<ResponseDTO> responseDTO = authService.login(loginDTO);

        ResponseEntity<ResponseDTO> response = ResponseUtils.wrap(responseDTO);
        log.debug("Successfully logged in. Response: {}", response);
        return  response;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> create(@RequestBody RegisterRequestDto requestDto) {
        log.debug("REST request to create new user");
        authService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUsername(@PathVariable UUID id) {
        log.debug("REST request to get username by id");
        Optional<String> username = authService.getUsername(id);

        ResponseEntity<String> response = ResponseUtils.wrap(username);
        log.debug("Response: {}", response);
        return response;
    }
}
