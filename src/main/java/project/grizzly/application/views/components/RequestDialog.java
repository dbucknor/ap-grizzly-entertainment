package project.grizzly.application.views.components;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.Customer;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.views.screens.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class RequestDialog extends JDialog implements IView {
    private JPanel infoPanel, btnPanel, mainPanel;
    private Box itemsPanel;
    private Button approve, cancel, delete;
    private JLabel rentalId, customerId, status, itemCount, estPrice, totalPrice, rentalDate, invoiceId;
    private JScrollPane scrollPane;
    private RentalRequest request;
    private TableController<RentalRequest, String> controller;
    private ThemeManager theme;

    public RequestDialog(TableController<RentalRequest, String> controller) {
        super(MainWindow.getInstance().getFrame(), "Rental Request", true);
        this.controller = controller;
        this.request = controller.getEditingRecord();
        this.theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        boolean isApproved = controller.getEditingRecord().isApproved();

        infoPanel = new JPanel(new GridLayout(4, 2));
        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainPanel = new JPanel(new BorderLayout());
        itemsPanel = new Box(BoxLayout.Y_AXIS);

        rentalId = new JLabel("Request Id: " + request.getRequestId());
        rentalId.setFont(theme.getFontLoader().getH3());

        invoiceId = new JLabel("Invoice Id: " + request.getInvoice().getInvoiceId());
        invoiceId.setFont(theme.getFontLoader().getH3());

        customerId = new JLabel("Customer Id: " + ((Customer) request.getInvoice().getCustomer()).getCustomerId());
        customerId.setFont(theme.getFontLoader().getH3());

        status = new JLabel("Approval Status: " + (isApproved ? "Approved" : "Not Approved")
                + " " + request.getInvoice().getCustomer().getLastName());
        status.setFont(theme.getFontLoader().getH3());

        itemCount = new JLabel("Total Items: " + request.getInvoice().getItems().size());
        itemCount.setFont(theme.getFontLoader().getH3());

        estPrice = new JLabel("Estimated Price: $ " + request.getInvoice().getTotalPrice());
        estPrice.setFont(theme.getFontLoader().getH3());

        totalPrice = new JLabel("Total Price: $ " + request.getInvoice().getTotalPrice());
        totalPrice.setFont(theme.getFontLoader().getH3());

        rentalDate = new JLabel("Request Date: " + request.getRequestDate().format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a")));
        rentalDate.setFont(theme.getFontLoader().getH3());

        cancel = new Button("Cancel", ButtonSize.SMALL);
        approve = new Button(isApproved ? "Reject" : "Approve", ButtonSize.SMALL);
        delete = new Button("Delete", ButtonSize.SMALL);

        itemsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (InvoiceItem it : request.getInvoice().getItems()
        ) {
            itemsPanel.add(new RequestListItem(it));
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        }

        scrollPane = new JScrollPane(itemsPanel);
    }

    @Override
    public void addComponents() {
        infoPanel.add(rentalId);
        infoPanel.add(rentalDate);
        infoPanel.add(invoiceId);
        infoPanel.add(itemCount);
        infoPanel.add(status);
        infoPanel.add(customerId);
        infoPanel.add(estPrice);
        infoPanel.add(totalPrice);

        btnPanel.add(approve);
        btnPanel.add(delete);
        btnPanel.add(cancel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        this.add(mainPanel);
    }

    @Override
    public void addListeners() {
        approve.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isApproved = controller.getEditingRecord().isApproved();
                approve.setText(isApproved ? "Reject" : "Approve");
                controller.getEditingRecord().setApproved(!isApproved);
                status.setText(!isApproved ? "Approval Status: Not Approved" : "Approval Status: Approved");
                controller.updateRecord(controller.getEditingRecord());
            }
        });
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteRecords(Collections.singletonList(Integer.toString(controller.getEditingRecord().getRequestId())));
            }
        });
        cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void setProperties() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setMaximumSize(new Dimension(900, 700));

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
