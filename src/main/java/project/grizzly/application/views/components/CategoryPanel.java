package project.grizzly.application.views.components;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.equipment.Equipment;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CategoryPanel extends Box implements IView {
    private ThemeManager theme;
    private Button all;
    private List<Equipment> equipments;
    private String title;
    //    private JPanel ;
    private Box header, items;
    private JLabel lbl;
    private CustomerScreenController controller;

    public CategoryPanel(String title, List<Equipment> equipments, CustomerScreenController controller) {
        super(BoxLayout.Y_AXIS);
        this.title = title;
        this.equipments = equipments;
        theme = ThemeManager.getInstance();
        this.controller = controller;

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        lbl = new JLabel(title);
        lbl.setForeground(theme.getCurrentScheme().getNeutralLight());

        header = new Box(BoxLayout.X_AXIS);
        header.setBackground(theme.getCurrentScheme().getPrimary());
        header.setOpaque(true);

        all = new Button("See All", ButtonSize.SMALL);
        items = new Box(BoxLayout.X_AXIS);
    }

    @Override
    public void addComponents() {
        header.add(Box.createRigidArea(new Dimension(50, 0)));
        header.add(lbl);
        header.add(Box.createHorizontalGlue());
        header.add(all);
        header.add(Box.createRigidArea(new Dimension(50, 0)));

        addItemsToPanel();

        this.add(Box.createHorizontalStrut(150));
        this.add(header);
        this.add(items);
        this.add(Box.createHorizontalStrut(150));
        this.setAlignmentY(Box.TOP_ALIGNMENT);
        this.setAlignmentX(Box.CENTER_ALIGNMENT);
    }

    private void addItemsToPanel() {
        items.removeAll();
        for (Equipment e : equipments
        ) {
            System.out.println("Added to panel;");
            items.add(new EquipmentBox<>(e, controller));
            items.add(Box.createHorizontalGlue());
        }
    }

    @Override
    public void addListeners() {
        all.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo change screen and search
            }
        });
    }

    @Override
    public void setProperties() {
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 300, 400));
        this.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 300, 400));
        this.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 300, 400));
        this.setBackground(theme.getCurrentScheme().getNeutralLight());

        this.setVisible(true);
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
        addItemsToPanel();
//        this.repaint();
        revalidate();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
