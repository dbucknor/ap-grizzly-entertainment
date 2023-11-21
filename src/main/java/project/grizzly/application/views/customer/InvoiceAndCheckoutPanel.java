package project.grizzly.application.views.customer;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class InvoiceAndCheckoutPanel extends JPanel {

    public InvoiceAndCheckoutPanel() {
        setLayout(new GridBagLayout());

        InvoicePanel invoicePanel = new InvoicePanel();
        CheckoutPanel checkoutPanel = new CheckoutPanel();

        GridBagConstraints invoiceConstraints = new GridBagConstraints();
        invoiceConstraints.gridx = 0;
        invoiceConstraints.gridy = 0;
        invoiceConstraints.weightx = 1;
        invoiceConstraints.weighty = 1;
        invoiceConstraints.fill = GridBagConstraints.BOTH;
        invoiceConstraints.insets = new Insets(5, 5, 5, 5);

        add(invoicePanel, invoiceConstraints);

        GridBagConstraints checkoutConstraints = new GridBagConstraints();
        checkoutConstraints.gridx = 1;
        checkoutConstraints.gridy = 0;
        checkoutConstraints.weightx = 0;
        checkoutConstraints.weighty = 0;
        checkoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        checkoutConstraints.anchor = GridBagConstraints.NORTH;
        checkoutConstraints.insets = new Insets(0, 20, 10, 20);

        add(checkoutPanel, checkoutConstraints);
    }
}
