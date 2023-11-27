package project.grizzly.application.views.customer;

import project.grizzly.application.models.Invoice;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.enums.ButtonSize;

import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.CartItem;
import project.grizzly.application.views.components.fields.Button;

import javax.swing.*;
import java.awt.*;

public class Cart extends JPanel implements IView {
    private JPanel itemsToRentPanel;
    private JLabel itemsLbl;
    private Box checkout, itemsList;
    private Button request, clear;
    private ThemeManager theme;
    private Invoice invoice;

    public Cart() {
        super(new GridBagLayout());
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    public void initializeComponents() {
        //items in cart
        itemsToRentPanel = new JPanel();
        itemsToRentPanel.setBackground(new Color(255, 255, 255));
        itemsToRentPanel.setPreferredSize(new Dimension(500, 640));
        itemsToRentPanel.setLayout(new GridBagLayout());

        itemsList = new Box(BoxLayout.X_AXIS);

        //checkout
        request = new Button("Make Request", ButtonSize.EXTEND);
        clear = new Button("Clear All", ButtonSize.EXTEND);

        checkout = new Box(BoxLayout.X_AXIS);
        checkout.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height));
        checkout.setBackground(theme.getCurrentScheme().getNeutralLight());

        checkout.add(pricePanel("Sub-Total:", Double.toString(invoice != null ? invoice.getTotalPrice() - invoice.getDiscount() : 0)));
        checkout.add(pricePanel("Discount:", Double.toString(0)));
        checkout.add(pricePanel("Total:", Double.toString(0)));

        checkout.add(Box.createRigidArea(new Dimension(0, 50)));
        checkout.add(request);
        checkout.add(clear);

        itemsLbl = new JLabel("Items To Rent");
        itemsLbl.setOpaque(true);
        itemsLbl.setBackground(theme.getCurrentScheme().getAccent1());
        itemsLbl.setForeground(theme.getCurrentScheme().getPrimary());
        itemsLbl.setPreferredSize(new Dimension(0, 20));
        itemsLbl.setFont(theme.getFontLoader().getH2().deriveFont(24f));
        itemsLbl.setBorder(BorderFactory.createEmptyBorder(4, 15, 4, 4));
    }

    public void addComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20, 20, 0, 20);
        itemsToRentPanel.add(itemsLbl, constraints);

        if (invoice != null) {
            for (InvoiceItem it : invoice.getItems()) {
                itemsList.add(new CartItem(it));
                itemsList.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        constraints.gridx = 1;
        constraints.insets = new Insets(20, 20, 10, 20);
        itemsToRentPanel.add(itemsList, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(itemsToRentPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(checkout, constraints);
    }

    public void addListeners() {

    }

    public void setProperties() {

    }

    private JPanel pricePanel(String title, String value) {
        JPanel panel = new JPanel();
        panel.setBackground(theme.getCurrentScheme().getAccent1());
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(54, 15));

        JLabel label = new JLabel(title);
        label.setForeground(theme.getCurrentScheme().getPrimary());

        JLabel valueLbl = new JLabel(value);
        valueLbl.setForeground(theme.getCurrentScheme().getPrimary());

        valueLbl.setBorder(null);
        valueLbl.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(label, BorderLayout.WEST);
        panel.add(valueLbl, BorderLayout.CENTER);

        return panel;
    }

}

