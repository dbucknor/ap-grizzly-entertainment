package project.grizzly.application.views.customer;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.Customer;
import project.grizzly.application.models.Invoice;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.enums.ButtonSize;

import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.CartItem;
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
    private Box totalPrice, subTotal, itemCount, discount;
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
        itemsPanel.setBackground(theme.getCurrentScheme().getNeutralLight());
        itemsPanel.setPreferredSize(new Dimension(Double.valueOf(Toolkit.getDefaultToolkit().getScreenSize().width * 0.6).intValue(), Toolkit.getDefaultToolkit().getScreenSize().height));
        itemsPanel.setMaximumSize(new Dimension(Double.valueOf(Toolkit.getDefaultToolkit().getScreenSize().width * 0.6).intValue(), Toolkit.getDefaultToolkit().getScreenSize().height));

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
        addInfoPanels();

        addItems();

        this.add(Box.createRigidArea(new Dimension(50, 0)));
        this.add(itemsPanel);
        this.add(Box.createHorizontalGlue());
        this.add(checkout);
        this.add(Box.createRigidArea(new Dimension(50, 0)));
    }

    private void addInfoPanels() {
        infoPanel.removeAll();
        infoPanel.add(pricePanel("# of Items: ", Integer.toString(controller.getInvoice().getItems().size())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Sub-Total:", "$ " + (controller.getInvoice().getTotalPrice() - controller.getInvoice().getDiscount())));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Discount:", "$ " + controller.getInvoice().getDiscount()));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(pricePanel("Total:", "$ " + controller.getInvoice().getTotalPrice()));
    }

    private void addItems() {
        itemsList.removeAll();
        for (InvoiceItem it : controller.getInvoice().getItems()) {
            itemsList.add(new CartItem(it));
            itemsList.add(Box.createRigidArea(new Dimension(0, 15)));
            System.out.println("add to view");
        }
    }

    public void addListeners() {
        controller.addListeners(new FieldListeners<List<InvoiceItem>>() {
            @Override
            public void onChange(List<InvoiceItem> fieldValue) {
                addItems();
                addInfoPanels();
            }

            @Override
            public void onBlur(List<InvoiceItem> fieldValue) {
            }
        });

        request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getInvoice() == null || controller.getInvoice().getItems().isEmpty()) {
                    return;
                }

                RentalRequest rentalReq = new RentalRequest(1, LocalDateTime.now(), false, controller.getInvoice(), null);
                rentalReq.setRequestFrom((Customer) AuthService.getInstance().getLoggedInUser());

                Invoice invoice = controller.getInvoice();
                invoice.setRentalRequest(rentalReq);
                invoice.setInvoiceDate(LocalDateTime.now());
                invoice.setDiscount(0);
                invoice.setCustomer((Customer) AuthService.getInstance().getLoggedInUser());

                rentalReq.setInvoice(invoice);
                controller.sendInvoice(invoice);
                controller.sendRequest(rentalReq);

                controller.emptyCart();
                addInfoPanels();
                revalidate();
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.emptyCart();
                addInfoPanels();
                revalidate();
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

