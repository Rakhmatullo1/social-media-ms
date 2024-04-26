package com.rakhmatullo.userservice.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDTO {
    @NotBlank(message = "username cannot be null")
    private String username;
    @NotBlank(message = "password not be null")
    private String password;
}
