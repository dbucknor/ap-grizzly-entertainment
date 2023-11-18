package project.grizzly.application.views.chats;

import project.grizzly.application.controllers.ChatController;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;

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
