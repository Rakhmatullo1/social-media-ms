package com.rakhmatullo.userservice.service.mapper;

import com.rakhmatullo.userservice.service.dto.LoginDTO;
import com.rakhmatullo.userservice.service.dto.LoginRequestDTO;
import com.rakhmatullo.userservice.service.dto.RegisterRequestDto;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    String DEFAULT_GROUP_NAME = "Users";

    LoginRequestDTO toLoginRequestDTO(LoginDTO loginDTO);

    @Mapping(target = "groups", expression = "java(setGroups())")
    @Mapping(target = "enabled", constant = "true", resultType = Boolean.class)
    @Mapping(target = "emailVerified", constant = "true", resultType = Boolean.class)
    @Mapping(target = "credentials", source = "password", qualifiedByName = "setCredentials")
    UserRepresentation toUserRepresentation(RegisterRequestDto requestDto);

    default List<String> setGroups() {
        return List.of(DEFAULT_GROUP_NAME);
    }

    @Named("setCredentials")
    default List<CredentialRepresentation> setCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(password);
        return List.of(credentialRepresentation);
    }
}
