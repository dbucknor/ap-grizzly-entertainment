package project.grizzly.application.views.screens;

import project.grizzly.application.models.User;
import project.grizzly.application.models.enums.UserType;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.services.AuthChangedListener;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.CustomCardLayout;
import project.grizzly.application.views.components.TransactionDialog;
import project.grizzly.application.views.components.UserRequests;
import project.grizzly.application.views.customer.CustomerScreen;
import project.grizzly.application.views.employee.EmployeeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.TimerTask;

public class MainWindow implements IView {
    private JFrame frame;
    private static MainWindow instance;
    private final ThemeManager themeManager;//TODO;
    private final AuthService authService; //TODO;
    private final ClassLoader loader;
    private final Logger logger;
    private static CustomCardLayout cardLayout;
    private BufferedImage logo;
    private LoadScreen loadScreen;
    private CustomerScreen customerScreen;
    private EmployeeScreen employeeScreen;
    private SignIn signIn;
    private final String LOAD_SCREEN = "Load-Screen";
    private final String SIGN_IN = "Sign-In";
    private final String EMPLOYEE_SCREEN = "Employee-Screen";
    private final String CUSTOMER_SCREEN = "Customer-Screen";
    private JMenuItem viewTransactions, viewRequests;
    private JMenu fileMenu;
    private JMenuBar menuBar;

    public MainWindow() {
        themeManager = ThemeManager.getInstance();
        authService = AuthService.getInstance();
        cardLayout = new CustomCardLayout();
        loader = MainWindow.class.getClassLoader();
        logger = LogManager.getLogger(MainWindow.class);

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
//            instance.start();
        }
        return instance;
    }

    public void initializeComponents() {
        frame = new JFrame("Grizzly Entertainment Equipment Rental Management");
        loadScreen = new LoadScreen();
        signIn = new SignIn();
        customerScreen = CustomerScreen.getInstance();
        employeeScreen = new EmployeeScreen();
        frame.setLayout(cardLayout);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        viewTransactions = new JMenuItem("View Transactions");
        viewRequests = new JMenuItem("View Rental Requests");

        viewTransactions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
        viewRequests.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));

        fileMenu.add(viewTransactions);
        fileMenu.add(viewRequests);
        menuBar.add(fileMenu);


    }

    public void addComponents() {
        frame.add(loadScreen);
        frame.add(signIn);
        frame.add(customerScreen);
        frame.add(employeeScreen);
        frame.setJMenuBar(menuBar);

        cardLayout.addLayoutComponent(loadScreen, LOAD_SCREEN);
        cardLayout.addLayoutComponent(signIn, SIGN_IN);
        cardLayout.addLayoutComponent(customerScreen, CUSTOMER_SCREEN);
        cardLayout.addLayoutComponent(employeeScreen, EMPLOYEE_SCREEN);

        cardLayout.show(frame.getContentPane(), LOAD_SCREEN);

//
        java.util.Timer timer = new java.util.Timer("load-screen-timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                switchFromLoading();
            }
        }, 5000);
    }

    private void switchFromLoading() {
        cardLayout.show(frame.getContentPane(), CUSTOMER_SCREEN);
    }

    public void addListeners() {
        authService.addAuthChangedListener(new AuthChangedListener<User>() {
            @Override
            public void onAuthChanged(User user) {
                if (user == null && cardLayout.getSelectedCardName().compareTo(SIGN_IN) != 0) {
                    cardLayout.show(frame.getContentPane(), SIGN_IN);
                } else {
                    if (cardLayout.isCurrentCard(SIGN_IN)) {
                        assert user != null;
                        switch (user.getAccountType()) {
                            case CUSTOMER -> cardLayout.show(frame.getContentPane(), CUSTOMER_SCREEN);
                            case EMPLOYEE -> cardLayout.show(frame.getContentPane(), EMPLOYEE_SCREEN);
                            default -> cardLayout.show(frame.getContentPane(), SIGN_IN);
                        }
                    }
                }
            }
        });

        viewTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = AuthService.getInstance().getLoggedInUser();
                new TransactionDialog();

                if (user != null && user.getAccountType() == UserType.CUSTOMER) {
                    new TransactionDialog();
                }
            }
        });

        viewRequests.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = AuthService.getInstance().getLoggedInUser();
                new UserRequests();
                if (user != null && user.getAccountType() == UserType.CUSTOMER) {
                    new UserRequests();
                }
            }
        });
    }

    @Override
    public void setProperties() {
        getImages();

        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(logo);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public static CardLayout getCardLayout() {
        return cardLayout;
    }

    private void getImages() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
