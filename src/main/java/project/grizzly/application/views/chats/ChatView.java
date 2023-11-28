package project.grizzly.application.views.chats;

import project.grizzly.application.controllers.ChatController;
import project.grizzly.application.models.Message;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.models.User;
import org.hibernate.HibernateException;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class ChatView extends JPanel {
//    private project.grizzly.application.views.components.fields.TextArea messageField;
//    private JPanel newMessage;
//    private Box messages;
//    private Button send, exit;
//    private final ChatController controller;
//    private final ThemeManager theme;
//    private final AuthService authService;
//    private User currentUser, receiver;
//
//    public ChatView(ChatController controller) {
//        super(new BorderLayout());
//        this.controller = controller;
//        this.theme = ThemeManager.getInstance();
//        this.authService = AuthService.getInstance();
//        currentUser = authService.getLoggedInUser();
//        receiver = controller.getOtherUser(currentUser);
//
//        initializeComponents();
//        addComponents();
//        addListeners();
//        setProperties();
//    }
//
//    @Override
//    public void initializeComponents() {
//        newMessage = new JPanel(new BorderLayout());
//        newMessage.setBackground(theme.getCurrentScheme().getNeutralLight());
//        newMessage.setVisible(true);
//
//        messages = new Box(BoxLayout.Y_AXIS);
//        messages.setBackground(theme.getCurrentScheme().getAccent1());
//        messages.setVisible(true);
//
//        send = new Button(Ionicons4IOS.SEND);
//        exit = new Button(FontAwesomeSolid.ARROW_LEFT);
//
//        messageField = new project.grizzly.application.views.components.fields.TextArea();
//    }
//
//    @Override
//    public void addComponents() {
//        addMessages();
//
//        newMessage.add(send, BorderLayout.EAST);
//        newMessage.add(messageField, BorderLayout.CENTER);
//
//
//        add(messages, BorderLayout.CENTER);
//        add(newMessage, BorderLayout.SOUTH);
//    }
//
//    private void addMessages() {
//        messages.add(Box.createRigidArea(new Dimension(0, 30)));
//
//        for (Message m :
//                controller.getMessages()) {
//            if (m.getSender().getUserId().compareTo(currentUser.getUserId()) == 0) {
//                messages.add(new MessageBox(m, SwingConstants.RIGHT), SwingConstants.RIGHT);
//            } else {
//                messages.add(new MessageBox(m, SwingConstants.LEFT), SwingConstants.LEFT);
//            }
//            messages.add(Box.createRigidArea(new Dimension(0, 30)));
//        }
//
//    }
//
//    @Override
//    public void addListeners() {
//        send.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String message = messageField.getValue() != null ? messageField.getValue().trim() : "";
//                if (message.isEmpty()) return;
//                Message m = new Message(null, message, LocalDateTime.now());
//                m.setSender(currentUser);
//                m.setChat(controller.getChat());
//
//                try {
//                    controller.insertMessage(m);
//                    messageField.setValue("");
//                } catch (HibernateException he) {
//                    //TODO
//                    System.out.println(he.getMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void setProperties() {
//        setVisible(true);
//
//    }
}
