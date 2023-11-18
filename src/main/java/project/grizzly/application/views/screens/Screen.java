package project.grizzly.application.views.screens;

import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.HeaderView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel implements IView {
    protected HeaderView headerView;
    protected JPanel container;
    protected ThemeManager theme;
    protected Logger logger;

    public Screen() {
        super(new BorderLayout());
        logger = LogManager.getLogger(Screen.class);
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
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
