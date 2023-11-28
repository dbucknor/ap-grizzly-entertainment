package project.grizzly.application.views.components;

import project.grizzly.application.models.User;
import project.grizzly.application.models.enums.UserType;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.services.AuthChangedListener;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.views.customer.CustomerScreen;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;
import org.kordamp.ikonli.swing.FontIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class HeaderView extends JPanel implements IView {

    private BufferedImage logo;
    private JLabel logoPanel;
    private JLabel lbl1, lbl2;
    private JPanel iconNavigation, homeNavigation;
    private boolean darkMode;
    private Button accIcon, cartIcon, modeIcon, messageIcon;
    protected ThemeManager theme = ThemeManager.getInstance();
    private final ClassLoader loader = HeaderView.class.getClassLoader();
    private User user;

    public HeaderView() {
        super(new BorderLayout());
        getImages();
        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    private void getImages() {
        try {
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeComponents() {
        lbl1 = new JLabel("Grizzly's Entertainment");
        lbl1.setFont(new Font("Gobold CUTS", Font.PLAIN, 22));
        lbl2 = new JLabel("Rental Management System", SwingConstants.CENTER);
        lbl2.setFont(new Font("Montserrat Light", Font.BOLD, 18));
        lbl1.setForeground(theme.getCurrentScheme().getNeutralLight());
        lbl2.setForeground(theme.getCurrentScheme().getNeutralLight());

        accIcon = new Button(FontAwesomeSolid.USER_CIRCLE);
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
        if (user != null && user.getAccountType() == UserType.EMPLOYEE) return;
        CustomerScreen.getInstance().changeScreen(CustomerScreen.CUSTOMER_HOME);
    }

    public void addComponents() {
        this.add(homeNavigation, BorderLayout.WEST);
        this.add(lbl2, BorderLayout.CENTER);
        this.add(iconNavigation, BorderLayout.EAST);
    }

    public void addListeners() {
        modeIcon.addActionListener((e) -> {
            darkMode = !darkMode;
            if (darkMode) {
                modeIcon.setFontIcon(FontIcon.of(Ionicons4IOS.MOON, 24, theme.getCurrentScheme().getPrimary()));
            } else {
                modeIcon.setFontIcon(FontIcon.of(Ionicons4IOS.SUNNY, 24, theme.getCurrentScheme().getPrimary()));
            }
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

        cartIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user != null && user.getAccountType() == UserType.EMPLOYEE) return;
                CustomerScreen.getInstance().changeScreen(CustomerScreen.REQUEST_CART);
            }
        });

        accIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthService.getInstance().logOut();
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

        AuthService.getInstance().addAuthChangedListener(new AuthChangedListener<User>() {
            @Override
            public void onAuthChanged(User authUser) {
                user = authUser;
            }
        });
    }

    public void setProperties() {
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
