package com.rakhmatullo.postsservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileService {
    Optional<Resource> upload(MultipartFile file);

    Optional<Resource> loadFile(String name);
}
