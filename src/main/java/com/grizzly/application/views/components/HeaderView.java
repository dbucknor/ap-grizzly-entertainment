package com.grizzly.application.views.components;

import com.grizzly.application.views.MainWindow;
import com.grizzly.application.theme.ThemeManager;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class HeaderView extends JPanel {

    BufferedImage logo, account, cart, viewMode, messages;
    JLabel logoPanel;
    JLabel lbl1, loadSpinner, lbl2, names;
    JPanel iconNavigation, homeNavigation;
    private boolean darkMode;
    private Button accIcon, cartIcon, modeIcon, messageIcon;
    protected ThemeManager theme = ThemeManager.getInstance();
    private final ClassLoader loader = HeaderView.class.getClassLoader();

    public HeaderView() {
        super(new BorderLayout());
        getImages();
        initComponents();
        addComponentsToPanel();
        addListeners();
        setProps();
    }

    private void getImages() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void initComponents() {
        lbl1 = new JLabel("Grizzly's Entertainment");
        lbl1.setFont(new Font("Gobold CUTS", Font.PLAIN, 22));
        lbl2 = new JLabel("Rental Management System", SwingConstants.CENTER);
        lbl2.setFont(new Font("Montserrat Light", Font.BOLD, 18));
        lbl1.setForeground(theme.getCurrentScheme().getNeutralLight());
        lbl2.setForeground(theme.getCurrentScheme().getNeutralLight());

        accIcon = new Button(Ionicons4IOS.PERSON);
        cartIcon = new Button(Ionicons4IOS.CART);
        messageIcon = new Button(Ionicons4IOS.CHATBUBBLES);
        modeIcon = new Button(Ionicons4IOS.SUNNY);


        iconNavigation = new JPanel(new GridLayout(1, 4));
        iconNavigation.setOpaque(false);
        homeNavigation = new JPanel(new BorderLayout(10, 0));

        logoPanel = new JLabel();
        homeNavigation.add(logoPanel, BorderLayout.WEST);
        homeNavigation.add(lbl1, BorderLayout.CENTER);
        homeNavigation.setOpaque(false);

        iconNavigation.add(accIcon);
        iconNavigation.add(messageIcon);
        iconNavigation.add(modeIcon);
        iconNavigation.add(cartIcon);
    }

    private void goHome() {
        MainWindow.getMainLayout().show(MainWindow.getInstance().getContentPane(), "Load-Screen");
    }

    private void addComponentsToPanel() {
        this.add(homeNavigation, BorderLayout.WEST);
        this.add(lbl2, BorderLayout.CENTER);
        this.add(iconNavigation, BorderLayout.EAST);
    }

    private void addListeners() {
        modeIcon.onClick((e) -> {
            darkMode = !darkMode;

            if (darkMode) {
                modeIcon.setButtonIcon(Ionicons4IOS.MOON);
            } else {
                modeIcon.setButtonIcon(Ionicons4IOS.SUNNY);
            }

            return null;
        });
        homeNavigation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goHome();
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                goHome();
                super.mousePressed(e);
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = e.getComponent().getHeight() / 2;
                int h = e.getComponent().getHeight() / 2;

                logoPanel.setIcon(new ImageIcon(logo.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                logoPanel.setPreferredSize(new Dimension(w, h));
                super.componentResized(e);
            }
        });
    }

    private void setProps() {
        this.setBackground(theme.getCurrentScheme().getPrimary());
        this.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 12));

        this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 60));
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 80));
        this.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 100));

        iconNavigation.setPreferredSize(new Dimension(320, iconNavigation.getParent().getHeight()));
        homeNavigation.setPreferredSize(new Dimension(320, iconNavigation.getParent().getHeight()));

        this.setVisible(true);
    }


}
