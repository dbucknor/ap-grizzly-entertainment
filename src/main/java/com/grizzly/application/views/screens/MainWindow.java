package com.grizzly.application.views.screens;

import com.grizzly.application.models.User;
import com.grizzly.application.services.AuthChangedListener;
import com.grizzly.application.services.AuthService;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.CustomCardLayout;
import com.grizzly.application.views.employee.EmployeeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class MainWindow extends JFrame {
    static Logger logger = LogManager.getLogger(MainWindow.class);
    private static CustomCardLayout mainLayout;
    private BufferedImage logo;
    private final ClassLoader loader = MainWindow.class.getClassLoader();
    private LoadScreen loadScreen;
    private CustomerHome customerHome;
    private EmployeeScreen employeeScreen;
    private SignIn signIn;
    private static MainWindow instance;
    private ThemeManager themeManager;
    private final AuthService authService; //TODO;

    public MainWindow() {
        super("Grizzly Rental Management System");
        themeManager = ThemeManager.getInstance();
        authService = AuthService.getInstance();
        mainLayout = new CustomCardLayout();
        this.setLayout(mainLayout);
        initializeComponents();
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    public static MainWindow getInstance() {
        System.out.println(instance);
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private void initializeComponents() {
        loadScreen = new LoadScreen();
        signIn = new SignIn();
        customerHome = new CustomerHome();
        employeeScreen = new EmployeeScreen();
    }

    private void addPanelsToWindow() {
        this.add(loadScreen);
        this.add(signIn);
        this.add(customerHome);
        this.add(employeeScreen);

        mainLayout.addLayoutComponent(loadScreen, "Load-Screen");
        mainLayout.addLayoutComponent(signIn, "Sign-In");
        mainLayout.addLayoutComponent(customerHome, "Customer-Home");
        mainLayout.addLayoutComponent(employeeScreen, "Employee-Screen");

//        mainLayout.show(this.getContentPane(), "Load-Screen");
//        mainLayout.show(this.getContentPane(), "Employee-Screen");

        Timer timer = new Timer("timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainLayout.show(getContentPane(), "Sign-In");
            }
        }, 5000);
    }

    private void setWindowProperties() {
        getImages();
        
//        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setIconImage(logo);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void registerListeners() {
        authService.addAuthChangedListener(new AuthChangedListener<User>() {
            @Override
            public void onAuthChanged(User user) {
                if (user == null) {
                    mainLayout.show(getContentPane(), "Sign-In");
                } else {
                    if (mainLayout.isCurrentCard("Sign-In")) {
                        switch (user.getAccountType()) {
                            case CUSTOMER -> mainLayout.show(getContentPane(), "Customer-Home");
                            case EMPLOYEE -> mainLayout.show(getContentPane(), "Employee-Screen");
                            default -> mainLayout.show(getContentPane(), "Sign-In");
                        }
                    }
                }
            }
        });
    }

    public static CardLayout getMainLayout() {
        return mainLayout;
    }

    private void getImages() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
