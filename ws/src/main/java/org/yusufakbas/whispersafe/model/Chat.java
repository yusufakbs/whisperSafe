package org.yusufakbas.whispersafe.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String chatName;
    private String chatImage;

    @ManyToMany
    private Set<Users> admins = new HashSet<>();

    @Column(name = "is_group")
    private boolean isGroup;
    @JoinColumn(name = "created_by")
    @ManyToOne
    private Users createdBy;

    @ManyToMany
    private Set<Users> users = new HashSet<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(Long id, String chatName, String chatImage, boolean isGroup, Users createdBy, Set<Users> users, List<Message> messages, Set<Users> admins) {
        this.id = id;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.isGroup = isGroup;
        this.createdBy = createdBy;
        this.users = users;
        this.admins = admins;
        this.messages = messages;
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

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Users> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Users> admins) {
        this.admins = admins;
    }
}
