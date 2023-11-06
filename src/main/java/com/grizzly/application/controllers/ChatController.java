package com.grizzly.application.controllers;

import com.grizzly.application.models.Chat;
import com.grizzly.application.models.Message;
import com.grizzly.application.models.User;
import com.grizzly.application.models.interfaces.IChatController;
import com.grizzly.application.services.CRUDService;
import com.grizzly.application.services.CombinedQuery;

import java.util.List;

public class ChatController implements IChatController {
    private Chat chat;
    private List<Message> messages;
    private List<Message> listeners;
    private CRUDService<Message, String> crudService;


    private ChatsController chatsController;

    public ChatController(Chat chat) {
        this.chat = chat;
        this.crudService = new CRUDService<>(Message.class);
        fetchMessages(chat.getChatId());
    }

    @Override
    public void insertMessage(Message message) {
        crudService.insert(message);
        refreshMessages();
    }

    @Override
    public void fetchMessages(String chatId) {
        try {
            messages = crudService.readWhere((s) ->
                    new CombinedQuery<Message>("SELECT ALL m FROM Message m")
                            .where("m.chatId", "=:chatId", chatId)
                            .orderBy("m.sentDate", "ASC")
                            .getQuery(s)
            );
        } catch (Exception e) {
            System.out.println("messages: " + e.getMessage());
        }
    }

    @Override
    public void deleteMessage(Message message) {
        crudService.delete(message.getMessageId());
        refreshMessages();
    }

    @Override
    public void deleteMessage(String messageId) {
        crudService.delete(messageId);
        refreshMessages();
    }

    public Chat getChat() {
        return chat;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void refreshMessages() {
        fetchMessages(chat.getChatId());

    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getOtherUser(User currentUser) {
        return chat.getUsers().stream().filter((u) -> u.getUserId().compareTo(currentUser.getUserId()) != 0).toList().get(0);
    }

    public CRUDService<Message, String> getCrudService() {
        return crudService;
    }

    public void setCrudService(CRUDService<Message, String> crudService) {
        this.crudService = crudService;
    }


    public ChatsController getChatsController() {
        return chatsController;
    }

    public void setChatsController(ChatsController chatsController) {
        this.chatsController = chatsController;
    }
}
