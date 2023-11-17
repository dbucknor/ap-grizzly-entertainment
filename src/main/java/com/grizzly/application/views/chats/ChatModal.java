package com.grizzly.application.views.chats;

import com.grizzly.application.controllers.ChatController;
import com.grizzly.application.models.Chat;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.theme.ThemeManager;

public class ChatModal implements IView {
    private final ThemeManager theme;
    private ChatController chatController;

    public ChatModal() {
        theme = ThemeManager.getInstance();

    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public void addComponents() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setProperties() {

    }
}
