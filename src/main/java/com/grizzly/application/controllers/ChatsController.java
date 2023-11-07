package com.grizzly.application.controllers;

import com.grizzly.application.models.Chat;
import com.grizzly.application.services.CRUDService;

import java.util.ArrayList;
import java.util.List;

public class ChatsController {
    private List<Chat> chats;
    private final List<ChatsListener> listeners;
    private Chat currentChat;
    private CRUDService<Chat, String> crudService;

    public ChatsController() {
        listeners = new ArrayList<>();
        crudService = new CRUDService<>(Chat.class);
        readAllChats();
    }

    public void insertChat(Chat chat) {
        crudService.insert(chat);
    }

    public Chat readChat(String chatId) {
        return crudService.read(chatId);
    }

    public void refresh() {
        readAllChats();
    }

    public void readAllChats() {
        chats = crudService.readALL();
        updateListeners();
    }

    public void updateChat(Chat chat) throws Exception {
        crudService.update(chat);
    }

    public void deleteChat(Chat chat) {

    }

    public void deleteChat(String chatId) {
        crudService.delete(chatId);
    }

    public List<ChatsListener> getListeners() {
        return listeners;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }

    public void setCurrentChat(Chat currentChat) {
        this.currentChat = currentChat;
    }

    public CRUDService<Chat, String> getCrudService() {
        return crudService;
    }

    public void setCrudService(CRUDService<Chat, String> crudService) {
        this.crudService = crudService;
    }

    private void updateListeners() {
        for (ChatsListener cl : listeners
        ) {
            if (cl != null) {
                cl.onUpdate(chats);
            }
        }
    }

    public void addListener(ChatsListener chatsListener) {
        listeners.add(chatsListener);
    }

    public void removeListener(ChatsListener chatsListener) {
        listeners.remove(chatsListener);
    }
}
