package com.rakhmatullo.postsservice.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private String username;
    private String photoUrl;
    private String fullName;
}
