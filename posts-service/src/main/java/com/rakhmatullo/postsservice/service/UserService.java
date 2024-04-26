package com.rakhmatullo.postsservice.service;

import com.rakhmatullo.postsservice.service.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDTO getUserInfo(UUID id);

    List<UUID> getAll();
}
