package com.grizzly.application.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Messages")
@Table(name = "Messages")
public class Message implements Serializable {
    @Id
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "message")
    private String message;
    @Column(name = "senderId")
    private String senderId;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "receiverId")
    private String receiverId;

    public Message() {
        message = null;
        messageId = null;
        senderId = null;
        date = null;
        receiverId = null;
    }

    public Message(String messageId, String message, String senderId, LocalDateTime date, String receiverId) {
        this.messageId = messageId;
        this.message = message;
        this.senderId = senderId;
        this.date = date;
        this.receiverId = receiverId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", senderId='" + senderId + '\'' +
                ", date=" + date +
                ", receiverId='" + receiverId + '\'' +
                '}';
    }
}
