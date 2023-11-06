package com.grizzly.application.views.chats;

import com.grizzly.application.models.Message;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.RoundedComponent;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;


public class MessageBox extends JPanel implements IView {
    private Message message;
    private final ThemeManager theme;
    private JLabel text, date, name;
    private int side;

    public MessageBox(Message message, int side) {
        super(new BorderLayout(0, 5));
        this.message = message;
        this.theme = ThemeManager.getInstance();
        this.side = side;
    }

    @Override
    public void initializeComponents() {
        text = new JLabel("<html> " + message.getMessage() + " </html>", setTextAlignLeft());
        text.setForeground(getTextColor());

        name = new JLabel(message.getSender().getEmail(), setTextAlignLeft());
        name.setForeground(getTextColor());

        date = new JLabel(message.getSentDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mma z")), setTextAlignRight());
        date.setForeground(getTextColor());
    }

    private int setTextAlignRight() {
        return side == SwingConstants.RIGHT ? SwingConstants.RIGHT : SwingConstants.LEFT;
    }

    private int setTextAlignLeft() {
        return side == SwingConstants.LEFT ? SwingConstants.LEFT : SwingConstants.RIGHT;
    }

    private Color getTextColor() {
        return side == SwingConstants.RIGHT ? theme.getCurrentScheme().getNeutralLight() : theme.getCurrentScheme().getPrimary();
    }


    @Override
    public void addComponents() {
        add(name, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);
        add(date, BorderLayout.SOUTH);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void paintAll(Graphics g) {
        RoundedComponent.paintCorners(g, 15, this);
        super.paintAll(g);
    }

    @Override
    public void setProperties() {
        setMinimumSize(new Dimension(350, 40));
        setPreferredSize(new Dimension(350, 40));
        setBackground(side == SwingConstants.RIGHT ? theme.getCurrentScheme().getPrimary() : theme.getCurrentScheme().getNeutralLight());
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }
}
