package org.yusufakbas.whispersafe.service;

import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.MessageException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Message;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException;

    public List<Message> getChatMessages(Long chatId, Users reqUser) throws ChatException, UserException;

    public Message findMessageById(Long messageId) throws MessageException;

    public void deleteMessageById(Long messageId, Users reqUser) throws MessageException, UserException;

}
