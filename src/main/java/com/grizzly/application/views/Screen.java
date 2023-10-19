package com.grizzly.application.views;

import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.HeaderView;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {
    protected HeaderView headerView;
    protected JPanel container;
    protected ThemeManager theme = ThemeManager.getInstance();

    public Screen() {
        super(new BorderLayout());
        initializeComponents();
        addComponents();
        setProps();
    }

    private void initializeComponents() {
        headerView = new HeaderView();
        container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setVisible(true);
    }

    private void addComponents() {
        this.add(headerView, BorderLayout.NORTH);
        this.add(container, BorderLayout.CENTER);
    }

    private void setProps() {
        this.setBackground(theme.getCurrentScheme().getAccent1());
        this.setMinimumSize(new Dimension(800, 600));
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMaximumSize(new Dimension(2160, 1440));
        this.setVisible(true);
    }
}
