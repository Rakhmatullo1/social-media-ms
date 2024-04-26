package com.rakhmatullo.postsservice.service;

import com.rakhmatullo.postsservice.entity.Post;
import com.rakhmatullo.postsservice.service.dto.PostDTO;
import com.rakhmatullo.postsservice.service.dto.request.PostRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PostService {
    Optional<PostDTO> create(PostRequestDTO requestDTO);

    Optional<PostDTO> getById(UUID id);

    Page<PostDTO> getAll(Pageable pageable);

    Post findById(UUID id);
}
