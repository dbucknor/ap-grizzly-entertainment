package project.grizzly.application.views.components;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.enums.ButtonSize;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.fields.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListItem extends Box implements IView {
    private InvoiceItem invoiceItem;
    private CustomerScreenController controller;
    private ThemeManager theme;
    private JLabel imageLbl, name, rentPeriod, price;
    private Button remove;
    private JPanel imgPan;
    private Box infoPan, box;

    public ListItem(InvoiceItem invoiceItem, CustomerScreenController controller) {
        super(BoxLayout.X_AXIS);
        this.invoiceItem = invoiceItem;
        this.controller = controller;
        this.theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }


    @Override
    public void initializeComponents() {
        imageLbl = new JLabel(invoiceItem.getEquipment().getImage());
        imageLbl.setMaximumSize(new Dimension(120, 120));
        name = new JLabel(invoiceItem.getEquipment().getName());
        rentPeriod = new JLabel(invoiceItem.getRentPeriod().formatted());

        price = new JLabel("Price: $ " + invoiceItem.getTotalPrice());
        price.setForeground(theme.getCurrentScheme().getPrimary());
        price.setFont(theme.getFontLoader().getH2().deriveFont(14f));

        remove = new Button("Remove", ButtonSize.SMALL);
        infoPan = new Box(BoxLayout.Y_AXIS);
        box = new Box(BoxLayout.X_AXIS);

        box.add(rentPeriod);
        box.add(remove);

        infoPan.add(name);
        infoPan.add(box);
    }

    @Override
    public void addComponents() {
        this.add(imageLbl);
        this.add(infoPan);

    }

    @Override
    public void addListeners() {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeFromRequest(invoiceItem);
            }
        });
    }

    @Override
    public void setProperties() {

    }

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public CustomerScreenController getController() {
        return controller;
    }

    public void setController(CustomerScreenController controller) {
        this.controller = controller;
    }

}
