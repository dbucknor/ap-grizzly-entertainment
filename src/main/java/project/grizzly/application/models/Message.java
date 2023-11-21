package project.grizzly.application.models;

import project.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Messages")
@Table(name = "Messages")
public class Message implements ITableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Integer messageId;
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

    public Message(Integer messageId, String message, LocalDateTime sentDate) {
        this.messageId = messageId;
        this.message = message;
        this.sentDate = sentDate;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
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
