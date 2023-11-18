package project.grizzly.application.views.components;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.equipment.Equipment;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class EquipmentBox<T extends Equipment> extends Box implements IView {
    private final ThemeManager theme;
    private final ClassLoader loader;
    private T equipment;
    private JLabel name, description, rentPrice, image;
    private Box btnBar, lb4;
    private Button info;
    private JPanel lb1, lb2, lb3;
    private CustomerScreenController controller;

    public EquipmentBox(T equipment, CustomerScreenController controller) {
        super(BoxLayout.Y_AXIS);
        this.equipment = equipment;
        this.theme = ThemeManager.getInstance();
        this.loader = this.getClass().getClassLoader();
        this.controller = controller;

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        lb1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lb2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lb3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lb4 = new Box(BoxLayout.X_AXIS);

        ImageIcon img = equipment.getImage() != null ? equipment.getImage() : getDefaultImage();
        img = new ImageIcon(img.getImage().getScaledInstance(220, 150, Image.SCALE_SMOOTH));

        image = new JLabel(img);
        image.setPreferredSize(new Dimension(220, 100));
        lb1.add(image);

//        name = new JLabel(equipment.getName());
        name = new JLabel("<html>" + equipment.getName() + "</html>");
        name.setForeground(theme.getCurrentScheme().getNeutralDark());
        name.setFont(theme.getFontLoader().getH3().deriveFont(12f));
        name.setOpaque(true);
        lb2.add(name);

//        description = new JLabel(equipment.getDescription());
        description = new JLabel("<html>" + equipment.getDescription() + "</html>");
        description.setForeground(theme.getCurrentScheme().getNeutralDark());
        description.setFont(theme.getFontLoader().getBODY().deriveFont(12f));
        description.setOpaque(true);
        lb3.add(description);

        rentPrice = new JLabel("$ " + equipment.getPrice() + "/" + equipment.getRentedPer().toString().toLowerCase());
        rentPrice.setForeground(theme.getCurrentScheme().getPrimary());
        rentPrice.setFont(theme.getFontLoader().getH2().deriveFont(14f));
        lb4.add(rentPrice);
        lb4.setOpaque(false);
        lb4.setAlignmentY(Box.CENTER_ALIGNMENT);

        info = new Button("Info", ButtonSize.SMALL);

        btnBar = new Box(BoxLayout.X_AXIS);
        btnBar.setAlignmentX(Box.CENTER_ALIGNMENT);
        btnBar.setAlignmentY(Box.CENTER_ALIGNMENT);
        btnBar.setBackground(theme.getCurrentScheme().getAccent1());
        btnBar.setOpaque(true);
    }

    @Override
    public void addComponents() {
        btnBar.add(Box.createRigidArea(new Dimension(10, 40)));
//        btnBar.add(Box.createVerticalStrut(20));
        btnBar.add(lb4);
        btnBar.add(Box.createHorizontalGlue());
        btnBar.add(info);
        btnBar.add(Box.createRigidArea(new Dimension(10, 0)));

        this.add(lb1);
        this.add(lb2);
        this.add(lb3);
        this.add(btnBar);
    }

    @Override
    public void addListeners() {
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoDialog(equipment, controller);
            }
        });
    }

    @Override
    public void setProperties() {
        this.setMinimumSize(new Dimension(250, 350));
        this.setPreferredSize(new Dimension(250, 350));
        this.setMaximumSize(new Dimension(250, 350));
        this.setBackground(Color.GRAY);
        this.setOpaque(true);
        this.setAlignmentX(Box.LEFT_ALIGNMENT);
        this.setAlignmentY(Box.TOP_ALIGNMENT);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics gg = RoundedComponent.paintCorners(g, 15, this);
        super.paint(gg);
    }

    private ImageIcon getDefaultImage() {
        try {
            BufferedImage im = ImageIO.read(new File(Objects.requireNonNull(loader.getResource("media/images/equipment-placeholder.png")).toURI().getPath()));
            return new ImageIcon(im);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public T getEquipment() {
        return equipment;
    }

    public void setEquipment(T equipment) {
        this.equipment = equipment;
    }
}
