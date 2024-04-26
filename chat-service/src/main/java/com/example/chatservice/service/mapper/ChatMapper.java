package com.example.chatservice.service.mapper;

import com.example.chatservice.entity.Chat;
import com.example.chatservice.entity.Message;
import com.example.chatservice.exception.NotFoundException;
import com.example.chatservice.service.UserService;
import com.example.chatservice.service.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@RequiredArgsConstructor
@Slf4j
public abstract class ChatMapper {

    private final UserService userService;

    @Mapping(target = "receiverPhotoUrl", source = "receiverId", qualifiedByName = "getReceiverPhotoUrl")
    @Mapping(target = "receiverName", source = "receiverId", qualifiedByName = "getReceiverName")
    @Mapping(target = "messageReceivedTime", source = "messages", qualifiedByName = "getMessageReceivedTime")
    @Mapping(target = "lastMessage", source = "messages", qualifiedByName = "getMessageContent")
    public abstract  ChatDTO toChatDTO(Chat chat);

    @Named("getReceiverName")
    String getReceiverName(UUID uuid) {
        return getUserData(uuid).getUsername();
    }

    @Named("getReceiverPhotoUrl")
    String getReceiverPhotoUrl(UUID id) {
        Map<String, List<String>> attributes = getUserData(id).getAttributes();

        List<String> userPhotoUrls = attributes.get("photoUrl");

        if(Objects.isNull(userPhotoUrls)) {
            log.warn("User photo Url is not found");
            return null;
        }

        return userPhotoUrls.stream().findFirst().orElse(null);
    }

    @Named("getMessageReceivedTime")
    String getMessageReceivedTime(List<Message> messages) {
        Optional<Message> message = sortListAndGetLastMessage(messages);

        if(message.isEmpty()){
            return null;
        }

        return message.get().getCreatedAt().toString();
    }

    @Named("getMessageContent")
    String getMessageContent(List<Message> messages) {
        Optional<Message> message = sortListAndGetLastMessage(messages);

        if(message.isEmpty()) {
            return null;
        }

        return message.get().getContent();
    }

    private Optional<Message> sortListAndGetLastMessage(List<Message> messages){
        if(Objects.isNull(messages)) {
            log.info("Last message is not found");
            return Optional.empty();
        }

        messages.sort(Comparator.comparing(Message::getCreatedAt).reversed());

        Message message = messages.stream().findFirst().orElse(null);
        return Optional.ofNullable(message);
    }

    private UserRepresentation getUserData(UUID id) {
        Optional<UserRepresentation>  user = userService.getUserInfo(id);

        if(user.isEmpty()) {
            log.warn("User is not found {}", id);
            throw new NotFoundException("User is not found with id {}" + id);
        }

        return user.get();
    }
}
