package project.grizzly.application.views.chats;

import project.grizzly.application.controllers.ChatController;
import project.grizzly.application.controllers.ChatsController;
import project.grizzly.application.models.Chat;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Chats extends JPanel implements IView {

    private final ChatsController controller;
    private ThemeManager theme;
    private Box chatsList;
    private ChatView currentChat;

    public Chats() {
        super(new BorderLayout());
        controller = new ChatsController();
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        chatsList = new Box(BoxLayout.Y_AXIS);
        chatsList.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height));
        chatsList.setBackground(theme.getCurrentScheme().getNeutralLight());
        chatsList.setOpaque(true);
        chatsList.setVisible(true);
    }

    private void addListItems() {
        for (Chat c : controller.getChats()
        ) {
            chatsList.add(createChatItem(c));
            chatsList.add(Box.createRigidArea(new Dimension(0, 10)));

        }
    }

    private JPanel createChatItem(Chat c) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.setCurrentChat(c);
                currentChat = new ChatView(new ChatController(c));
                add(currentChat, BorderLayout.CENTER);
                super.mouseClicked(e);
            }
        });
        panel.add(new JLabel(c.getChatId()), BorderLayout.CENTER);
        panel.setOpaque(true);
        return panel;
    }

    @Override
    public void addComponents() {
        chatsList.add(new JLabel("Chats"));
        chatsList.add(Box.createRigidArea(new Dimension(0, 50)));
        addListItems();
        add(chatsList, BorderLayout.EAST);
    }

    @Override
    public void addListeners() {
    }

    @Override
    public void setProperties() {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
    }
}
