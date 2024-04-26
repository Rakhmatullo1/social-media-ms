package com.rakhmatullo.postsservice.service;

import com.rakhmatullo.postsservice.service.dto.ContentDTO;
import com.rakhmatullo.postsservice.service.dto.MimeTypeWithResourceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface ContentService {
    Optional<ContentDTO> storeContent(UUID postId, MultipartFile file);

    Optional<MimeTypeWithResourceDTO> loadById(UUID id);

    Page<ContentDTO> getByPostId(UUID id, Pageable pageable);
}
