package com.grizzly.application.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Chat")
@Table(name = "Chat")
public class Chat implements Serializable {
    @Id
    @Column(name = "chatId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String chatId;
    @Transient
//    @ManyToMany(mappedBy = "",)
    private Set<User> users;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Message> messages;

    public Chat() {
        users = new HashSet<>();
        messages = new HashSet<>();
    }

    public Chat(User customer, User employee, HashSet<Message> messages) {
        users = new HashSet<>();
        users.add(customer);
        users.add(employee);
        this.messages = messages;
    }

    public Chat(User customer, User employee) {
        users = new HashSet<>();
        users.add(customer);
        users.add(employee);
        this.messages = new HashSet<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }


    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
