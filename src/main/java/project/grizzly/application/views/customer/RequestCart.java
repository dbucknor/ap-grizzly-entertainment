package project.grizzly.application.views.customer;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.enums.ButtonSize;

import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.ListItem;
import project.grizzly.application.views.components.fields.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class RequestCart extends Box implements IView {
    private JLabel itemsLbl;
    private JScrollPane scrollPane;
    private Box itemsPanel, checkout, itemsList, infoPanel, btnPanel;
    private Button request, clear;
    private ThemeManager theme;
    private CustomerScreenController controller;

    public RequestCart() {
        super(BoxLayout.X_AXIS);
        theme = ThemeManager.getInstance();
        this.controller = CustomerScreenController.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    public void initializeComponents() {
        itemsLbl = new JLabel("Items To Rent");
        itemsPanel = new Box(BoxLayout.Y_AXIS);
        itemsList = new Box(BoxLayout.Y_AXIS);
        scrollPane = new JScrollPane(itemsList);

        clear = new Button("Clear Items", ButtonSize.EXTEND);
        request = new Button("Make Request", ButtonSize.EXTEND);
        btnPanel = new Box(BoxLayout.Y_AXIS);

        btnPanel.add(request);
        btnPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        btnPanel.add(clear);


        checkout = new Box(BoxLayout.Y_AXIS);
        infoPanel = new Box(BoxLayout.Y_AXIS);


        checkout.add(Box.createVerticalStrut(50));
        checkout.add(infoPanel);
        checkout.add(Box.createRigidArea(new Dimension(0, 80)));
        checkout.add(btnPanel);
        checkout.setOpaque(true);

        checkout.setBackground(theme.getCurrentScheme().getNeutralLight());
        checkout.setPreferredSize(new Dimension(300, Toolkit.getDefaultToolkit().getScreenSize().height));
        checkout.setMaximumSize(new Dimension(300, Toolkit.getDefaultToolkit().getScreenSize().height));

        itemsPanel.add(itemsLbl);
        itemsPanel.add(scrollPane);
    }

    public void addComponents() {
        infoPanel.add(pricePanel("# of Items: ", Integer.toString(controller.getInvoice().getItems().size())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Sub-Total:", "$ " + (controller.getInvoice().getTotalPrice() - controller.getInvoice().getDiscount())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Discount:", "$ " + controller.getInvoice().getDiscount()));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Total:", "$ " + controller.getInvoice().getTotalPrice()));

        addItems();

        this.add(Box.createRigidArea(new Dimension(50, 0)));
        this.add(itemsPanel);
        this.add(Box.createHorizontalGlue());
        this.add(checkout);
        this.add(Box.createRigidArea(new Dimension(50, 0)));
    }

    private void addItems() {
        itemsList.removeAll();
        for (InvoiceItem it : controller.getInvoice().getItems()) {
            itemsList.add(new ListItem(it, controller));
            itemsList.add(Box.createRigidArea(new Dimension(0, 15)));
            System.out.println("add to view");
        }
    }

    public void addListeners() {
        controller.addListeners(new FieldListeners<List<InvoiceItem>>() {
            @Override
            public void onChange(List<InvoiceItem> fieldValue) {
                addItems();
            }

            @Override
            public void onBlur(List<InvoiceItem> fieldValue) {
            }
        });

        request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RentalRequest rr = new RentalRequest(null, LocalDateTime.now(), false, controller.getInvoice(), null);
                controller.getInvoice().setRentalRequest(rr);
                controller.getInvoice().setInvoiceDate(LocalDateTime.now());
                controller.getInvoice().setDiscount(0);

                rr.setInvoice(controller.getInvoice());
                controller.sendRequest(rr);
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.emptyCart();
            }
        });
    }

    public void setProperties() {

    }

    private Box pricePanel(String title, String value) {
        Box panel = new Box(BoxLayout.X_AXIS);
        panel.setBackground(theme.getCurrentScheme().getAccent1());
        panel.setOpaque(true);

        JLabel label = new JLabel(title);
        label.setForeground(theme.getCurrentScheme().getPrimary());
        label.setFont(theme.getFontLoader().getH2().deriveFont(16));

        JLabel valueLbl = new JLabel(value);
        valueLbl.setForeground(theme.getCurrentScheme().getPrimary());
        valueLbl.setFont(theme.getFontLoader().getLIGHT().deriveFont(20f));
        valueLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(valueLbl);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        return panel;
    }

}

