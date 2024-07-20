package org.yusufakbas.whispersafe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class ChatDto {
    private Long id;
    @JsonProperty("chat_name")
    private String chatName;
    @JsonProperty("chat_image")
    private String chatImage;
    private boolean isGroup;
    private UserDto createdBy;
    private Set<UserDto> users;
    private List<Long> messages;
    private Set<UserDto> admins;

    public ChatDto() {
    }

    public ChatDto(Long id, String chatName, String chatImage, boolean isGroup, UserDto createdBy, Set<UserDto> users,
            List<Long> messages, Set<UserDto> admins) {
        this.id = id;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.isGroup = isGroup;
        this.createdBy = createdBy;
        this.users = users;
        this.messages = messages;
        this.admins = admins;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    public List<Long> getMessages() {
        return messages;
    }

    public void setMessages(List<Long> messages) {
        this.messages = messages;
    }

    public Set<UserDto> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<UserDto> admins) {
        this.admins = admins;
    }
}
