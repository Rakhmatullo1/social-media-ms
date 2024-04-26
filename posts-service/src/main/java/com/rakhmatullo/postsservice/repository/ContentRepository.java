package com.rakhmatullo.postsservice.repository;

import com.rakhmatullo.postsservice.entity.Content;
import com.rakhmatullo.postsservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
    Page<Content> findByPost(Post post, Pageable pageable);
}
