package org.yusufakbas.whispersafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yusufakbas.whispersafe.dto.ChatDto;
import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.mapper.ChatDtoMapper;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.GroupChatRequest;
import org.yusufakbas.whispersafe.request.SingleChatRequest;
import org.yusufakbas.whispersafe.response.ApiResponse;
import org.yusufakbas.whispersafe.service.ChatService;
import org.yusufakbas.whispersafe.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
        }


    @PostMapping("/single")
    public ResponseEntity<ChatDto> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        Users reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
        System.out.println(reqUser.getId());
        System.out.println(singleChatRequest.getUserId());
        ChatDto chatDto = ChatDtoMapper.toChatDto(chat);
        return new ResponseEntity<>(chatDto, HttpStatus.OK);

    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChatHandler(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        Users reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createGroup(groupChatRequest, reqUser);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }


    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Long chatId, GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        Chat chat = chatService.findChatById(chatId);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);

    }


    @GetMapping("/user")
    public ResponseEntity<Set<ChatDto>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        Users reqUser = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        Set<ChatDto> chatDtos = ChatDtoMapper.toChatDtos(new HashSet<>(chats));

        return new ResponseEntity<>(chatDtos, HttpStatus.OK);
    }


    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Long chatId, @PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Users reqUser = userService.findUserProfile(jwt);
        Chat chats = chatService.addUserToGroup(userId, chatId, reqUser);

        return new ResponseEntity<Chat>(chats, HttpStatus.OK);

    }

    @DeleteMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Long chatId, @PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Users reqUser = userService.findUserProfile(jwt);
        Chat chats = chatService.removeFromGroup(chatId, userId, reqUser);

        return new ResponseEntity<Chat>(chats, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Long chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Users reqUser = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse response = new ApiResponse("Chat is deleted successfully", true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
