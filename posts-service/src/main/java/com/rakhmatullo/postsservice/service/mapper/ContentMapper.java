package com.rakhmatullo.postsservice.service.mapper;

import com.rakhmatullo.postsservice.entity.Content;
import com.rakhmatullo.postsservice.service.dto.ContentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContentMapper{

    @Mapping(target = "createdAt", expression = "java(content.getCreatedAt().toString())")
    ContentDTO toContentDTO(Content content);

}
