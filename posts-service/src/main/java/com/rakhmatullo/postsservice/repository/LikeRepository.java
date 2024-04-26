package com.rakhmatullo.postsservice.repository;

import com.rakhmatullo.postsservice.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Favorite, UUID> {
}
