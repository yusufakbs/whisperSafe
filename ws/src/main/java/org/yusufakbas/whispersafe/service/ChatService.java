package org.yusufakbas.whispersafe.service;

import org.yusufakbas.whispersafe.exception.ChatException;
import org.yusufakbas.whispersafe.exception.UserException;
import org.yusufakbas.whispersafe.model.Chat;
import org.yusufakbas.whispersafe.model.Users;
import org.yusufakbas.whispersafe.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    public Chat createChat(Users reqUser, Long userIdTwo) throws UserException;

    public Chat findChatById(Long id) throws UserException;

    public List<Chat> findAllChatByUserId(Long userId) throws UserException;

    public Chat createGroup(GroupChatRequest request, Users reqUser) throws UserException;

    public Chat addUserToGroup(Long userId, Long chatId, Users reqUser) throws UserException, ChatException;

    public Chat renameGroup(Long chatId, String groupName, Users reqUser) throws UserException, ChatException;

    public Chat removeFromGroup(Long chatId, Long userId, Users reqUser) throws UserException, ChatException;

    public void deleteChat(Long chatId, Long userId) throws ChatException, UserException;


}
