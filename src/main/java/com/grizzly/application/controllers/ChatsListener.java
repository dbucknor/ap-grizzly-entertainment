package com.grizzly.application.controllers;

import com.grizzly.application.models.Chat;

import java.util.List;

public interface ChatsListener {
    void onUpdate(List<Chat> chats);
}
