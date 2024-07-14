package org.yusufakbas.whispersafe.service;

import org.springframework.stereotype.Service;
import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.MessageException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Message;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.repository.MessageRepository;
import org.yusufakbas.whispersafe.request.SendMessageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException {
        Users user = userService.findUserById(request.getUserId());
        Chat chat = chatService.findChatById(request.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setUsers(user);
        message.setContent(request.getContent());
        message.setTimeStamp(LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> getChatMessages(Long chatId, Users reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You do not releted to this chat" + chatId);
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());

        return messages;
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Optional<Message> optional = messageRepository.findById(messageId);

        if (optional.isPresent()) {
            return optional.get();
        }
        throw new MessageException("Message not found" + messageId);
    }

    @Override
    public void deleteMessageById(Long messageId, Users reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);
        if (message.getUsers().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't delete another user's message" + reqUser.getUsername());
    }

}
