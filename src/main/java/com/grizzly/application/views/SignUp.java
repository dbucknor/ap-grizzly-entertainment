package com.grizzly.application.views;

import com.grizzly.application.views.components.Button;
import com.grizzly.application.views.components.ButtonSize;
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

public class SignUp extends Screen{
    private TextField fname,lname,phoneNum,email,password,retype;
    private Button btn1,btn2;

    private BufferedImage logo;
    private JLabel logoPanel,label1;
    private JPanel formPanel;
    private final ClassLoader loader = SignUp.class.getClassLoader();


    public SignUp(){
        placeComponents();
        layoutComponents();
        addListeners();
        addLogo();
    }
    private void placeComponents(){
        logoPanel=new JLabel();
        logoPanel.setVisible(true);


        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(theme.getCurrentScheme().getNeutralLight());
        formPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 3, Toolkit.getDefaultToolkit().getScreenSize().height));
        formPanel.setVisible(true);

        label1=new JLabel("Sign Up");
        label1.setFont(new Font("Gobold CUTS", Font.PLAIN, 34));

        fname=new TextField("First Name: ",null);
        lname=new TextField("Last Name: ",null);
        email=new TextField("Email: ",null);
        phoneNum=new TextField("Phone Number: ",null);
        password=new TextField("Password: ",true);
        retype=new TextField("Re-Type Password: ",true);

        btn1=new Button("Sign Up",ButtonSize.EXTEND);
        btn2=new Button("Login",ButtonSize.EXTEND);
        btn1.setButtonColor(theme.getCurrentScheme().getAccent1(), theme.getCurrentScheme().getPrimary());
    }
    private void layoutComponents(){
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(10, 0, 5, 0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(logoPanel, constraints);

        constraints.insets = new Insets(5, 0, 10, 0);

        constraints.gridy = 1;
        formPanel.add(label1, constraints);

        constraints.insets = new Insets(5, 0, 5, 0);

        constraints.gridy = 2;
        formPanel.add(fname, constraints);

        constraints.gridy = 3;
        formPanel.add(lname, constraints);

        constraints.gridy = 4;
        formPanel.add(email, constraints);

        constraints.gridy = 5;
        formPanel.add(password, constraints);

        constraints.gridy = 6;
        formPanel.add(retype, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btn1, constraints);

        constraints.gridy = 8;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(btn2, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        container.setLayout(new FlowLayout());
        container.add(formPanel);
    }

    public void addListeners(){
        formPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int w = (int) (e.getComponent().getWidth() / 2.5);
                int h = (int) (e.getComponent().getWidth() / 2.5);
                logoPanel.setIcon(new ImageIcon(logo.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                logoPanel.setPreferredSize(new Dimension(w, h));
                super.componentResized(e);
            }
        });
    }
    private void addLogo() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
