package com.rakhmatullo.postsservice.service.impl;

import com.rakhmatullo.postsservice.entity.Post;
import com.rakhmatullo.postsservice.repository.PostRepository;
import com.rakhmatullo.postsservice.security.SecurityUtils;
import com.rakhmatullo.postsservice.service.PostService;
import com.rakhmatullo.postsservice.service.UserService;
import com.rakhmatullo.postsservice.service.dto.NotificationDTO;
import com.rakhmatullo.postsservice.service.dto.NotificationRequestDTO;
import com.rakhmatullo.postsservice.service.dto.PostDTO;
import com.rakhmatullo.postsservice.service.dto.UserDTO;
import com.rakhmatullo.postsservice.service.dto.request.PostRequestDTO;
import com.rakhmatullo.postsservice.service.enums.NotificationType;
import com.rakhmatullo.postsservice.service.exceptions.CustomException;
import com.rakhmatullo.postsservice.service.exceptions.NotFoundException;
import com.rakhmatullo.postsservice.service.integration.NotificationClientService;
import com.rakhmatullo.postsservice.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserService userService;
    private final PostRepository postRepository;
    private final NotificationClientService notificationClientService;


    @Override
    public Optional<PostDTO> create(PostRequestDTO requestDTO) {
        log.info("Requested to create new post {}", requestDTO);
        Post post = postRepository.save(postMapper.toPost(requestDTO));

        sendNotifications2allUsers(post.getId());

        PostDTO postDTO = getPostDTO(post);
        log.info("Successfully created new post");
        return Optional.of(postDTO);
    }

    private void sendNotifications2allUsers(UUID postId){
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        userService
                .getAll().stream()
                .filter(id->!Objects.equals(id, currentUserId))
                .forEach(id->{
                    log.info("Requested to send notification to user {}", id);
                    pushNotification(postId, id);
                });
    }

    private void pushNotification(UUID postId, UUID toUserId) {
        log.debug("Started create notifications");
        NotificationRequestDTO notification = new NotificationRequestDTO();
        notification.setType(NotificationType.NEW_POST);
        notification.setUserId(SecurityUtils.getCurrentUserId());
        notification.setPostId(postId);
        notification.setToUserId(toUserId);

        Mono<NotificationDTO> response = notificationClientService.create(notification).orElseThrow(() -> {
            log.warn("Response from notification service empty");
            throw new CustomException("Some thing went wrong");
        });

        response.block();
        log.debug("Successfully completed");
    }

    @Override
    public Optional<PostDTO> getById(UUID id) {
        log.info("Requested to get post by id. ID: {}", id);
        Optional<Post> post = postRepository.findById(id);

        if(post.isEmpty()){
            log.warn("Post is not found");
            throw new NotFoundException("Post is not found "+ id);
        }

        PostDTO postDTO = getPostDTO(post.get());
        log.info("Successfully fetched post");
        return Optional.of(postDTO);
    }

    @Override
    public Page<PostDTO> getAll(Pageable pageable) {
        log.info("Requested to get all posts");

        if (Objects.isNull(pageable)) {
            log.warn("Invalid argument is passed! Pageable must not be null!");
            throw new CustomException("Invalid argument is passed! Pageable must not be null!");
        }

        Page<PostDTO> posts = postRepository.findAll(pageable).map(this::getPostDTO);
        log.info("Successfully fetched all posts");
        return posts;
    }

    @Override
    public Post findById(UUID id) {
        return postRepository.findById(id).orElseThrow(()->new NotFoundException("Post is not found"));
    }

    private PostDTO getPostDTO(Post post) {
        UserDTO userDTO = userService.getUserInfo(post.getUserId());
        return postMapper.toPostDTO(post, userDTO);
    }
}
