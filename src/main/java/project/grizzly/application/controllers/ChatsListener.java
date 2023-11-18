package project.grizzly.application.controllers;

import project.grizzly.application.models.Chat;

import java.util.List;

public interface ChatsListener {
    void onUpdate(List<Chat> chats);
}
