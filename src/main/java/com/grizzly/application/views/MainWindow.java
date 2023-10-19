package com.grizzly.application.views;

import com.grizzly.application.theme.FontLoader;
import com.grizzly.application.theme.ThemeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    static Logger logger = LogManager.getLogger(MainWindow.class);
    private static CardLayout mainLayout;
    private LoadScreen loadScreen;
    private SignIn signIn;
    private static MainWindow instance;
    private ThemeManager themeManager;

    public MainWindow() {
        super("Grizzly Rental Management System");
        themeManager = ThemeManager.getInstance();
        mainLayout = new CardLayout();
        this.setLayout(mainLayout);
        initializeComponents();
        addPanelsToWindow();
        setWindowProperties();
        registerListeners();
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private void initializeComponents() {
        loadScreen = new LoadScreen();
        signIn = new SignIn();
    }

    private void addPanelsToWindow() {
        this.add(loadScreen);
        mainLayout.addLayoutComponent(loadScreen, "Load-Screen");
        //  mainLayout.show(this.getContentPane(), "Load-Screen");

        this.add(signIn);
        mainLayout.addLayoutComponent(signIn, "Form-Screen");
        mainLayout.show(this.getContentPane(), "Form-Screen");


//        Timer timer = new Timer("timer");
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                mainLayout.show(getContentPane(), "Form-Screen");
//            }
//        }, 3000);

    }

    private void setWindowProperties() {

//        this.setSize(800, 800);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setBackground(Color.BLACK);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void registerListeners() {

    }

    public static CardLayout getMainLayout() {
        return mainLayout;
    }
}
