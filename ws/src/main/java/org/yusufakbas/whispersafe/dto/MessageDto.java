package org.yusufakbas.whispersafe.dto;

import java.time.LocalDateTime;

public class MessageDto {
    private Long id;
    private String content;
    private LocalDateTime timeStamp;
    private Long userId;
    private Long chatId;

    public MessageDto(Long id, String content, LocalDateTime timeStamp, Long userId, Long chatId) {
        this.id = id;
        this.content = content;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
