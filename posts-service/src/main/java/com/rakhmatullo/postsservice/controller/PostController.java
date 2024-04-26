package com.rakhmatullo.postsservice.controller;

import com.rakhmatullo.postsservice.controller.utils.ResponseUtils;
import com.rakhmatullo.postsservice.service.PostService;
import com.rakhmatullo.postsservice.service.dto.PostDTO;
import com.rakhmatullo.postsservice.service.dto.request.PostRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<PostDTO> create(@RequestBody PostRequestDTO postRequestDTO) {
        log.debug("REST request to create new post");
        Optional<PostDTO> postDTO = postService.create(postRequestDTO);

        ResponseEntity<PostDTO> response = ResponseUtils.wrapResponse(postDTO);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostDTO>> getPosts(@ParameterObject Pageable pageable) {
        log.debug("REST request to get all posts");
        Page<PostDTO> posts = postService.getAll(pageable);

        ResponseEntity<List<PostDTO>> response = ResponseUtils.wrap(posts);
        log.debug("Response {}", response);
        return response;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") UUID id) {
        log.debug("REST request to get post by id");
        Optional<PostDTO> post = postService.getById(id);

        ResponseEntity<PostDTO> response = ResponseUtils.wrapResponse(post);
        log.debug("Response: {}", response);
        return response;
    }
}
