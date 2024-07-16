package org.yusufakbas.whispersafe.mapper;

import org.yusufakbas.whispersafe.dto.MessageDto;
import org.yusufakbas.whispersafe.dto.UserDto;
import org.yusufakbas.whispersafe.model.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageDtoMapper {

    public static MessageDto toMessageDto(Message message) {
        if (message == null) {
            return null;
        }

        UserDto userDto = UserDtoMapper.toUserDto(message.getUsers());

        return new MessageDto(message.getId(), message.getContent(), message.getTimeStamp(), userDto.getId(), message.getChat().getId());
    }

    public static List<MessageDto> toMessageDtos(List<Message> messages) {
        return messages.stream().map(MessageDtoMapper::toMessageDto).collect(Collectors.toList());
    }
}
