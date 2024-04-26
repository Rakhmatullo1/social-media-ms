package com.rakhmatullo.postsservice.service.impl;

import com.rakhmatullo.postsservice.entity.Content;
import com.rakhmatullo.postsservice.entity.Post;
import com.rakhmatullo.postsservice.repository.ContentRepository;
import com.rakhmatullo.postsservice.service.ContentService;
import com.rakhmatullo.postsservice.service.FileService;
import com.rakhmatullo.postsservice.service.PostService;
import com.rakhmatullo.postsservice.service.dto.ContentDTO;
import com.rakhmatullo.postsservice.service.dto.MimeTypeWithResourceDTO;
import com.rakhmatullo.postsservice.service.enums.MimeType;
import com.rakhmatullo.postsservice.service.exceptions.CustomException;
import com.rakhmatullo.postsservice.service.exceptions.NotFoundException;
import com.rakhmatullo.postsservice.service.mapper.ContentMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final PostService postService;
    private final FileService fileService;
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;

    @SneakyThrows
    @Override
    public Optional<ContentDTO> storeContent(UUID postId, MultipartFile file) {
        log.info("Requested to upload content to post {}", postId);

        Post post  = postService.findById(postId);

        Optional<Resource> resource = fileService.upload(file);

        if(resource.isEmpty()) {
            log.warn("File is not stored");
            throw new CustomException("File is not stored");
        }

        Content content = new Content();
        content.setUrl(String.valueOf(resource.get().getURI()));
        content.setMain(false);
        content.setCreatedAt(new Date(System.currentTimeMillis()));
        content.setPost(post);
        content.setMimeType(findMimeType(file.getContentType()));

        content = contentRepository.save(content);

        return Optional.of(contentMapper.toContentDTO(content));
    }

    @Override
    public Optional<MimeTypeWithResourceDTO> loadById(UUID id) {
        log.info("Requested to get content by id {}", id);
        Content content  = contentRepository.findById(id).orElseThrow(()->new NotFoundException("Content is not found"));

        Optional<Resource> resource = fileService.loadFile(content.getUrl());

        if(resource.isEmpty()){
            log.warn("File is not found");
            throw new NotFoundException("File is not found");
        }

        MimeTypeWithResourceDTO resourceDTO = new  MimeTypeWithResourceDTO();
        resourceDTO.setResource(resource.get());
        resourceDTO.setMimeType(content.getMimeType());

        log.info("Successfully fetched content by id");
        return Optional.of(resourceDTO);
    }

    @Override
    public Page<ContentDTO> getByPostId(UUID id, Pageable pageable) {
        log.info("Requested to get contents by post id");
        Post post = postService.findById(id);

        Page<ContentDTO> contents = contentRepository.findByPost(post, pageable).map(contentMapper::toContentDTO);

        log.info("Successfully fetched all data");
        return contents;
    }

    private MimeType findMimeType(String contentType) {
        return Arrays.stream(MimeType.values())
                .filter(type-> Objects.equals(contentType, type.getType().toString()))
                .findFirst()
                .orElseThrow(()->new NotFoundException("Content type is not found"));
    }
}
