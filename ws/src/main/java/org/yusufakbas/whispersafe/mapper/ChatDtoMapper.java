package org.yusufakbas.whispersafe.mapper;

import org.yusufakbas.whispersafe.dto.ChatDto;
import org.yusufakbas.whispersafe.dto.UserDto;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Message;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatDtoMapper {

    public static ChatDto toChatDto(Chat chat) {
        if (chat == null) {
            return null;
        }

        UserDto createdBy = UserDtoMapper.toUserDto(chat.getCreatedBy());
        Set<UserDto> userDtos = UserDtoMapper.toUserDtos(chat.getUsers());
        Set<UserDto> adminDtos = UserDtoMapper.toUserDtos(chat.getAdmins());
        List<Long> messageIds = chat.getMessages().stream().map(Message::getId).collect(Collectors.toList());

        return new ChatDto(chat.getId(), chat.getChatName(), chat.getChatImage(), chat.isGroup(), createdBy, userDtos, messageIds, adminDtos);
    }

    public static Set<ChatDto> toChatDtos(Set<Chat> chats) {
        return chats.stream().map(ChatDtoMapper::toChatDto).collect(Collectors.toSet());
    }
}