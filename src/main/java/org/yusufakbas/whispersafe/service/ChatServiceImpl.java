package org.yusufakbas.whispersafe.service;

import org.springframework.stereotype.Service;
import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.repository.ChatRepository;
import org.yusufakbas.whispersafe.request.GroupChatRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;


    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat createChat(Users reqUser, Long userIdTwo) throws UserException {

        Users user = userService.findUserById(userIdTwo);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
        if (isChatExist != null) {
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return null;
    }

    @Override
    public Chat findChatById(Long id) throws UserException {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            return chat.get();
        }
        throw new UserException("Chat not found with id " + id);
    }

    @Override
    public List<Chat> findAllChatByUserId(Long userId) throws UserException {
        Users user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, Users reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatImage(request.getChatImage());
        group.setChatName(request.getChatName());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);
        for (Long userId : request.getUserIds()) {
            Users user = userService.findUserById(userId);
            group.getUsers().add(user);
        }

        return group;
    }

    @Override
    public Chat addUserToGroup(Long userId, Long chatId, Users userReq) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        Users user = userService.findUserById(userId);


        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            if (chat.getAdmins().contains(userReq)) {
                chat.getUsers().add(user);
                return chat;
            } else {
                throw new UserException("You are not admin ");
            }
        }

        throw new UserException("Chat not found with id " + chatId);
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, Users reqUser) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            if (chat.getUsers().contains(reqUser)) {
                chat.setChatName(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }

        throw new UserException("Chat not found with id " + chatId);

    }

    @Override
    public Chat removeFromGroup(Long chatId, Long userId, Users reqUser) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        Users user = userService.findUserById(userId);

        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if (user.getId().equals(reqUser.getId())) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            } else {
                throw new UserException("You are not remove another user");
            }
        }
        throw new UserException("Chat not found with id " + chatId);
    }

    @Override
    public void deleteChat(Long chatId, Users reqUser) throws ChatException, UserException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            chatRepository.deleteById(chat.getId());
        }
    }

}
