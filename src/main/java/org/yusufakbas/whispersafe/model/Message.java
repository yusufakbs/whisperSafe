package org.yusufakbas.whispersafe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    private LocalDateTime timeStamp;

    @ManyToOne
    private Users users;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message() {

    }

    public Message(Long id, String content, LocalDateTime timeStamp, Users users, Chat chat) {
        this.id = id;
        this.content = content;
        this.timeStamp = timeStamp;
        this.users = users;
        this.chat = chat;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
