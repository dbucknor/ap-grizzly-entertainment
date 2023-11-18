package project.grizzly.application.views.screens;

import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.TextField;

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

public class LoadScreen extends JPanel {
    BufferedImage logo, background;
    ImageIcon spinner;
    JLabel logoPanel, lbl1, loadSpinner, lbl2, names;

    protected ThemeManager theme = ThemeManager.getInstance();
    project.grizzly.application.views.components.fields.TextField f = new TextField("Input", "");
    private ClassLoader loader = LoadScreen.class.getClassLoader();


    public LoadScreen() {
        super(new GridBagLayout());
        getImages();
        initComponents();
        addListeners();
        addComponentsToPanel();
        setProps();
    }

    private void initComponents() {
        logoPanel = new JLabel();
        loadSpinner = new JLabel();

        lbl1 = new JLabel("Grizzly's Entertainment");
        lbl1.setFont(new Font("Gobold CUTS", Font.PLAIN, 64));
        lbl2 = new JLabel("Rental Management System");
        lbl2.setFont(new Font("Montserrat Light", Font.PLAIN, 28));
        lbl1.setForeground(theme.getCurrentScheme().getNeutralLight());
        lbl2.setForeground(theme.getCurrentScheme().getNeutralLight());
    }

    private void addListeners() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = e.getComponent().getWidth() / 5;
                int h = e.getComponent().getWidth() / 5;

                spinner.setImage(spinner.getImage().getScaledInstance(w / 4, h / 4, Image.SCALE_DEFAULT));
                loadSpinner.setIcon(spinner);
                loadSpinner.setPreferredSize(new Dimension(w / 4, h / 4));

                logoPanel.setIcon(new ImageIcon(logo.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                logoPanel.setPreferredSize(new Dimension(w, h));
                super.componentResized(e);
            }
        });
    }

    private void addComponentsToPanel() {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 10;
        constraints.gridx = 10;
        this.add(logoPanel, constraints);

        constraints.gridy = 11;
        constraints.gridx = 10;
        this.add(lbl1, constraints);

        constraints.gridy = 12;
        constraints.gridx = 10;
        this.add(lbl2, constraints);

        constraints.gridy = 14;
        constraints.gridx = 10;
        this.add(loadSpinner, constraints);
    }

    private void getImages() {
        try {
            background = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/load-background.png")).toURI().getPath()));
            logo = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/logo.png")).toURI().getPath()));
            spinner = new ImageIcon(loader.getResource("media/images/spinner.gif").toURI().toURL());

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProps() {
        this.setMinimumSize(new Dimension(800, 600));
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setMaximumSize(new Dimension(2160, 1440));
        this.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
