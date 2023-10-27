package com.grizzly.application.views;

import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.HeaderView;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel implements IView {
    protected HeaderView headerView;
    protected JPanel container;
    protected ThemeManager theme = ThemeManager.getInstance();

    public Screen() {
        super(new BorderLayout());
//        initializeBaseComponents();
//        addBaseComponents();
        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }
//
//    private void initializeBaseComponents() {
//
//    }

    private void addBaseComponents() {

    }

    @Override
    public void initializeComponents() {
        headerView = new HeaderView();
        container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setVisible(true);
    }

    @Override
    public void addComponents() {
        this.add(headerView, BorderLayout.NORTH);
        this.add(container, BorderLayout.CENTER);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setProperties() {
        this.setBackground(theme.getCurrentScheme().getAccent1());
        this.setMinimumSize(new Dimension(800, 600));
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMaximumSize(new Dimension(2160, 1440));
        this.setVisible(true);
    }
}
