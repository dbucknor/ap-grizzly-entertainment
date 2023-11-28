package project.grizzly.application.views.components;

import org.apache.commons.lang3.math.NumberUtils;
import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.*;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.enums.UserType;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.views.screens.MainWindow;
import project.grizzly.server.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class RequestDialog extends JDialog implements IView {
    private JPanel infoPanel, btnPanel, mainPanel;
    private Box itemsPanel;
    private Button approve, cancel, delete;
    private JLabel rentalId, customerId, status, itemCount, estPrice, totalPrice, rentalDate, invoiceId;
    private JScrollPane scrollPane;
    private RentalRequest request;
    private TableController<RentalRequest, Integer> controller;
    private ThemeManager theme;

    public RequestDialog(TableController<RentalRequest, Integer> controller) {
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

        status = new JLabel("Approval Status: " + (isApproved ? "Approved" : "Not Approved"));
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
        approve = new Button("Approve", ButtonSize.SMALL);
        delete = new Button("Delete", ButtonSize.SMALL);

        itemsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (InvoiceItem it : request.getInvoice().getItems()
        ) {
            itemsPanel.add(new RequestListItem(it));
            itemsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        }

        scrollPane = new JScrollPane(itemsPanel);

        User user = AuthService.getInstance().getLoggedInUser();
        if (user.getAccountType() == UserType.EMPLOYEE) {
            approve.setText(isApproved ? "Reject" : "Approve");
        } else {
            Transaction trn = request.getTransaction();
            if (request.isApproved() && trn != null && trn.getTotalPrice() < trn.getPaid()) {
                approve.setText("Pay");
            } else {
                approve.setEnabled(false);
            }
        }
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
                User user = AuthService.getInstance().getLoggedInUser();
                if (user == null) {
                    return;
                }
                if (user.getAccountType() == UserType.EMPLOYEE) {
                    handleApproval();
                } else {
                    handlePayment(user);
                }
            }
        });
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteRecords(Collections.singletonList(controller.getEditingRecord().getRequestId()));
            }
        });
        cancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void handlePayment(User user) {
        Customer c = (Customer) user;
        String value = JOptionPane.showInputDialog(null, "Please Enter Payment Amount", "Payment", JOptionPane.QUESTION_MESSAGE);

        if (NumberUtils.isParsable(value)) {
            try {
                Transaction transaction = request.getTransaction();

                if (transaction == null) {
                    transaction = new Transaction(1, Double.parseDouble(value), controller.getEditingRecord(), controller.getEditingRecord().getInvoice().getTotalPrice() - Double.parseDouble(value), LocalDateTime.now());
                    transaction.setRequest(request);
                    transaction.setOwner(c);
                    transaction.setCustomerId(c.getCustomerId());
                    c.getTransactions().add(transaction);

                    controller.getClient().sendRequest(new Request("ADD", "TRANSACTION", transaction));

                } else {
                    transaction.setPaid(Double.parseDouble(value));
                    transaction.setBalance(controller.getEditingRecord().getInvoice().getTotalPrice() - Double.parseDouble(value));
                    transaction.setTransactionDate(LocalDateTime.now());

                    transaction.setOwner(c);
                    transaction.setCustomerId(c.getCustomerId());
                    c.getTransactions().add(transaction);

                    controller.getClient().sendRequest(new Request("UPDATE", "TRANSACTION", transaction));
                }

                dispose();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Error making payment", "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter only numbers for payment value", "Invalid Payment Value", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleApproval() {
        boolean isApproved = controller.getEditingRecord().isApproved();
        controller.getEditingRecord().setApproved(!isApproved);
        controller.getEditingRecord().setApprovedBy((Employee) AuthService.getInstance().getLoggedInUser());

        controller.getExecutorService().submit(() -> {
            try {
                createTransaction();
                controller.updateRecord(controller.getEditingRecord());

                SwingUtilities.invokeLater(() -> {
                    approve.setText(isApproved ? "Reject" : "Approve");
                    status.setText(!isApproved ? "Approval Status: Not Approved" : "Approval Status: Approved");
                });

            } catch (InterruptedException e) {
                controller.getEditingRecord().setApproved(false);
                controller.getEditingRecord().setApprovedBy(null);
                controller.getEditingRecord().setTransaction(null);
            }
        });
    }

    private void createTransaction() throws InterruptedException {
        Transaction transaction = controller.getEditingRecord().getTransaction();
        if (transaction != null) {
            transaction.setOwner(controller.getEditingRecord().getRequestFrom());
            controller.getClient().sendRequest(new Request("UPDATE", "TRANSACTION", transaction));
        } else {
            transaction = new Transaction(0, 0.0, controller.getEditingRecord(), controller.getEditingRecord().getInvoice().getTotalPrice(), LocalDateTime.now());
            transaction.setOwner(controller.getEditingRecord().getRequestFrom());
            controller.getClient().sendRequest(new Request("ADD", "TRANSACTION", transaction));
        }
        controller.getEditingRecord().setTransaction(transaction);
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
