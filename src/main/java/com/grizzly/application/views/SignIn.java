package com.grizzly.application.views;

import com.grizzly.application.controllers.ResourceLoader;
import com.grizzly.application.views.components.*;
import com.grizzly.application.views.components.Button;
import com.grizzly.application.views.components.TextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class SignIn extends Screen {
    private TextField userName, password;
    private SelectField<String> loginType;
    private Button button1, button2;
    private BufferedImage logo;
    private JLabel lbl1, logoPanel, lbl2;
    private JPanel formPanel;
    private final ClassLoader loader = SignIn.class.getClassLoader();

    public SignIn() {
        getImages();
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        logoPanel = new JLabel();
        logoPanel.setVisible(true);

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(theme.getCurrentScheme().getNeutralLight());
        formPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height));
        formPanel.setVisible(true);

        lbl1 = new JLabel("Log In");
        lbl1.setFont(new Font("Gobold CUTS", Font.PLAIN, 34));

        userName = new TextField("Email:", null);
        password = new TextField("Password:", true);

        button1 = new Button("Sign In", ButtonSize.EXTEND);
        button2 = new Button("Sign Up", ButtonSize.EXTEND);
        button2.setButtonColor(theme.getCurrentScheme().getAccent1(), theme.getCurrentScheme().getPrimary());

        String[] options = {"Customer", "Employee"};

        loginType = new SelectField<>(options, options[0], null);

    }

    private void addComponents() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(10, 0, 5, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(logoPanel, constraints);

        constraints.insets = new Insets(5, 0, 10, 0);

        constraints.gridy = 1;
        formPanel.add(lbl1, constraints);

        constraints.insets = new Insets(5, 0, 5, 0);

        constraints.gridy = 2;
        formPanel.add(loginType, constraints);

        constraints.gridy = 3;
        formPanel.add(userName, constraints);

        constraints.gridy = 4;
        formPanel.add(password, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(button1, constraints);

        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(button2, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        container.setLayout(new FlowLayout());
        container.add(formPanel);
    }


    protected void addListeners() {

        /*
         * Resize image based on formPanel size
         */
        formPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = (int) (e.getComponent().getWidth() / 2.5);
                int h = (int) (e.getComponent().getWidth() / 2.5);
                logoPanel.setIcon(new ImageIcon(logo.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                logoPanel.setPreferredSize(new Dimension(w, h));
                super.componentResized(e);
            }
        });
    }

    /**
     * Retrieve logo from resources
     */
    private void getImages() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
