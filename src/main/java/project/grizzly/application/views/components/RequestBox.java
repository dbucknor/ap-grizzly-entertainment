package project.grizzly.application.views.components;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

public class RequestBox extends JPanel implements IView {
    private JLabel requestId, invoiceId, customerName, requestDate;
    private RentalRequest request;
    private ThemeManager theme;
    private TableController<RentalRequest, Integer> controller;

    public RequestBox(RentalRequest request, TableController<RentalRequest, Integer> controller) {
        super(new GridLayout(2, 2));
        this.request = request;
        this.controller = controller;
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        customerName = new JLabel("Customer: " + request.getInvoice().getCustomer().getFirstName() + " " + request.getInvoice().getCustomer().getLastName());
        requestId = new JLabel("Request Id: " + request.getRequestId());
        invoiceId = new JLabel("Invoice Id: " + request.getInvoice().getInvoiceId());
        requestDate = new JLabel("Request Date: " + request.getRequestDate().format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a")));
    }

    @Override
    public void addComponents() {
        this.add(requestId);
        this.add(requestDate);
        this.add(invoiceId);
        this.add(customerName);
    }

    @Override
    public void addListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.setEditingRecord(request);
                new RequestDialog(controller);
                super.mousePressed(e);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(RoundedComponent.paintCorners(g, 10, this));
    }

    @Override
    public void setProperties() {
        this.setBackground(theme.getCurrentScheme().getNeutralLight());
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
    }


}
