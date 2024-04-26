package com.rakhmatullo.postsservice.service.mapper;

import com.rakhmatullo.postsservice.service.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "photoUrl", source = "attributes", qualifiedByName = "getPhotoUrl")
    @Mapping(target = "fullName", source = "userRepresentation", qualifiedByName = "getFullName" )
    UserDTO toUserDTO(UserRepresentation userRepresentation);

    @Named("getFullName")
    default String getFullName(UserRepresentation userRepresentation) {
        return userRepresentation.getFirstName() + userRepresentation.getLastName();
    }

    @Named("getPhotoUrl")
    default String getPhotoUrl(Map<String, List<String>> attributes){
        if(Objects.isNull(attributes)) {
            return null;
        }

        List<String> photoUrl = attributes.get("photoUrl");

        if(Objects.isNull(photoUrl) || photoUrl.isEmpty()) {
            return null;
        }

        return photoUrl.get(0);
    }
}
