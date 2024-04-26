package com.rakhmatullo.postsservice.service.mapper;

import com.rakhmatullo.postsservice.entity.Favorite;
import com.rakhmatullo.postsservice.entity.Post;
import com.rakhmatullo.postsservice.security.SecurityUtils;
import com.rakhmatullo.postsservice.service.dto.PostDTO;
import com.rakhmatullo.postsservice.service.dto.UserDTO;
import com.rakhmatullo.postsservice.service.dto.request.PostRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "contents", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(setUserId())")
    @Mapping(target = "private", constant = "false", resultType = Boolean.class)
    @Mapping(target = "createdAt", expression = "java(setCreatedTime())")
    Post toPost(PostRequestDTO postRequestDTO);

    @Mapping(target = "username", source = "userDTO.username")
    @Mapping(target = "userPhotoUrl", source = "userDTO.photoUrl")
    @Mapping(target = "likesCount", source = "post.favorites", qualifiedByName = "getLikesCount")
    @Mapping(target = "createdAt",expression = "java(post.getCreatedAt().toString())")
    PostDTO toPostDTO(Post post, UserDTO userDTO);

    default Date setCreatedTime() {
        return new Date(System.currentTimeMillis());
    }

    default UUID setUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    @Named("getLikesCount")
    default int getLikesCount(Set<Favorite> favorites) {
        if(Objects.isNull(favorites) || favorites.isEmpty()) {
            return 0;
        }

        return favorites.size();
    }
}
