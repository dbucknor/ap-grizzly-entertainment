package com.grizzly.application.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grizzly.application.models.enums.FormFieldType;
import com.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Messages")
@Table(name = "Messages")
public class Message implements ITableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "message")
    private String message;
    @Column(name = "sentDate")
    private LocalDateTime sentDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "senderId", referencedColumnName = "userId")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId")
    private Chat chat;

    public Message() {
        message = null;
        messageId = null;
        sentDate = null;
    }

    public Message(String messageId, String message, LocalDateTime sentDate) {
        this.messageId = messageId;
        this.message = message;
        this.sentDate = sentDate;
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

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime date) {
        this.sentDate = date;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", date=" + sentDate +
                '}';
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public Message deserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, this.getClass());
    }

    @Transient
    @Override
    public Object[] getValues() {
        return new Object[]{messageId, sender.getUserId(), message, sentDate};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Message Id:", "Sender Id:", "Receiver Id:", "Message:", "Sent Date:"};
    }

//    @Override
//    public FieldConfig[] getFieldConfigs() {
////        return new FieldConfig[]{
////                new FieldConfig(String.class, "setMessageId", "Message Id", FormFieldType.TEXT, true),
////                new FieldConfig(String.class, "setSenderId", "Sender Id:", FormFieldType.TEXT),
////                new FieldConfig(String.class, "setReceiverId", "Receiver Id:", FormFieldType.TEXT),
////                new FieldConfig(String.class, "setMessage", "Message:", FormFieldType.TEXT),
////                new FieldConfig(String.class, "setSentDate", "Sent Date:", FormFieldType.TEXT),
////        };
//        return null;
//    }

    @Transient
    @Override
    public TableConfig createEntityTableCfg() {
        return null;
    }
}
