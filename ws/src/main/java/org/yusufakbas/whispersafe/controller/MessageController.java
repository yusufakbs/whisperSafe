package org.yusufakbas.whispersafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yusufakbas.whispersafe.dto.MessageDto;
import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.MessageException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.mapper.MessageDtoMapper;
import org.yusufakbas.whispersafe.model.Message;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.SendMessageRequest;
import org.yusufakbas.whispersafe.response.ApiResponse;
import org.yusufakbas.whispersafe.service.MessageService;
import org.yusufakbas.whispersafe.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDto> sendMessageHandler(@RequestBody SendMessageRequest sendMessageRequest, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Users reqUser = userService.findUserProfile(jwt);
        sendMessageRequest.setUserId(reqUser.getId());
        Message message = messageService.sendMessage(sendMessageRequest);
        MessageDto messageDto = MessageDtoMapper.toMessageDto(message);

        return new ResponseEntity<MessageDto>(messageDto, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable Long chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Users user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatMessages(chatId, user);


        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Long messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
        Users user = userService.findUserProfile(jwt);
        messageService.deleteMessageById(messageId, user);
        ApiResponse response = new ApiResponse("Message deleted successfully", false);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
