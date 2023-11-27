package project.grizzly.application.views.components;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.equipment.Equipment;
import project.grizzly.application.models.equipment.RentPeriod;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.views.components.fields.DateTimePicker;
import project.grizzly.application.views.screens.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class InfoDialog extends JDialog implements IView {
    JLabel name, price, status, condition;
    Button add, cancel;
    DateTimePicker start, end;
    private Equipment equipment;
    private ThemeManager theme;
    private CustomerScreenController controller;

    public InfoDialog(Equipment equipment, CustomerScreenController controller) {
        super(MainWindow.getInstance().getFrame(), "Equipment Info", true);
        this.controller = controller;
        theme = ThemeManager.getInstance();
        this.equipment = equipment;

        SwingUtilities.invokeLater(() -> {
            initializeComponents();
            addComponents();
            addListeners();
            setProperties();
        });

    }

    @Override
    public void initializeComponents() {
        this.setLayout(new GridLayout(4, 2));
        name = new JLabel("<html> Equipment Name: " + equipment.getName() + "</html>");
        name.setForeground(theme.getCurrentScheme().getNeutralDark());
        name.setFont(theme.getFontLoader().getH3().deriveFont(12f));
        name.setOpaque(true);

        condition = new JLabel("Condition: " + equipment.getCondition().toString());
        condition.setForeground(theme.getCurrentScheme().getNeutralDark());
        condition.setFont(theme.getFontLoader().getBODY().deriveFont(12f));
        condition.setOpaque(true);

        status = new JLabel("Rented Per: " + equipment.getRentalStatus().toString());
        status.setForeground(theme.getCurrentScheme().getNeutralDark());
        status.setFont(theme.getFontLoader().getBODY().deriveFont(12f));
        status.setOpaque(true);

        price = new JLabel("Price: $ " + equipment.getPrice() + "/" + equipment.getRentedPer().toString().toLowerCase());
        price.setForeground(theme.getCurrentScheme().getPrimary());
        price.setFont(theme.getFontLoader().getH2().deriveFont(14f));

        add = new Button("Add", ButtonSize.SMALL);
        cancel = new Button("Close", ButtonSize.SMALL);

        start = new DateTimePicker("Rent Start Date:", false, false, LocalDateTime.now(), LocalDateTime.now().plusYears(20));
        end = new DateTimePicker("Rent End Date:", false, false, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusYears(20));
    }

    @Override
    public void addComponents() {
        this.add(name);
        this.add(condition);
        this.add(status);
        this.add(price);
        this.add(start);
        this.add(end);
        this.add(add);
        this.add(cancel);
    }

    @Override
    public void addListeners() {
        add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.addToRequest(new InvoiceItem(0, false, equipment, 1, new RentPeriod(start.getValue(), end.getValue())));
                    JOptionPane.showMessageDialog(null, "Added to Request!");
                    dispose();
                } catch (Exception iss) {
                    JOptionPane.showMessageDialog(null, iss.getStackTrace(), "Date Entry Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void setProperties() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.setSize(new Dimension(800, 600));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
