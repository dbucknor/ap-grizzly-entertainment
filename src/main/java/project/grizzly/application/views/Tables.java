package project.grizzly.application.views;

import project.grizzly.application.models.*;
import project.grizzly.application.models.equipment.Light;
import project.grizzly.application.models.equipment.Power;
import project.grizzly.application.models.equipment.Sound;
import project.grizzly.application.models.equipment.Stage;
import project.grizzly.application.models.interfaces.TableFrameListener;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.views.components.CustomCardLayout;
import project.grizzly.application.views.components.fields.Button;
import project.grizzly.application.views.components.table.TableFrame;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import project.grizzly.application.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * All database tables screen
 */
public class Tables implements IView {
    private Button invoiceItems, equipment, user, invoice, transaction, requests;
    private final ThemeManager theme;
    private JPanel buttons, mainPanel;
    private JTabbedPane equipmentTables, userTables;
    private final CustomCardLayout cardLayout;
    private TableFrame soundEquipmentTable, stageEquipmentTable, employeeTable, lightEquipmentTable, powerEquipmentTable;
    private TableFrame invoiceItemTable, invoiceTable, customerTable, transactionTable, requestTable;

    public Tables() {
        cardLayout = new CustomCardLayout();
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
        mainPanel = new JPanel(cardLayout);
        equipmentTables = new JTabbedPane();
        userTables = new JTabbedPane();

        soundEquipmentTable = new TableFrame<Sound, String>("Sound Equipments", Sound.class);
        soundEquipmentTable.removeHeader(true);

        stageEquipmentTable = new TableFrame<Stage, String>("Stage Equipments", Stage.class);
        stageEquipmentTable.removeHeader(true);

        lightEquipmentTable = new TableFrame<Light, String>("Light Equipments", Light.class);
        lightEquipmentTable.removeHeader(true);

        powerEquipmentTable = new TableFrame<Power, String>("Power Equipments", Power.class);
        powerEquipmentTable.removeHeader(true);

        customerTable = new TableFrame<Customer, String>("Customer", Customer.class);
        customerTable.removeHeader(true);
        employeeTable = new TableFrame<Employee, String>("Employee", Employee.class);
        employeeTable.removeHeader(true);

        invoiceTable = new TableFrame<Invoice, Integer>("Invoices", Invoice.class);
        requestTable = new TableFrame<RentalRequest, Integer>("Rental Requests", RentalRequest.class);
        transactionTable = new TableFrame<Transaction, Integer>("Transactions", Transaction.class);
        invoiceItemTable = new TableFrame<InvoiceItem, Integer>("Invoice Items", InvoiceItem.class);

        buttons = new JPanel(new GridBagLayout());

        invoiceItems = createButton(FontAwesomeSolid.TASKS);
        invoice = createButton(FontAwesomeSolid.FILE_INVOICE_DOLLAR);
        user = createButton(FontAwesomeSolid.USERS);
        equipment = createButton(FontAwesomeSolid.TOOLS);
        requests = createButton(FontAwesomeSolid.QUESTION);
        transaction = createButton(FontAwesomeSolid.HANDSHAKE);
    }

    private Button createButton(Ikon icon) {
        Button b = new Button(icon);
        b.setButtonColor(theme.getCurrentScheme().getNeutralLight(), theme.getCurrentScheme().getPrimary());
        b.setIconSize(90);
        return b;
    }

    @Override
    public void addComponents() {
        createTabbedPane();

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(50, 50, 50, 50);
        buttons.add(bigButton(invoice, "Invoices"), constraints);

        constraints.gridx = 1;
        buttons.add(bigButton(invoiceItems, "Invoice Items"), constraints);

        constraints.gridx = 2;
        buttons.add(bigButton(equipment, "Equipment"), constraints);

        constraints.gridx = 3;
        buttons.add(bigButton(requests, "Requests"), constraints);

        constraints.gridx = 4;
        buttons.add(bigButton(transaction, "Transactions"), constraints);

        constraints.gridx = 5;
        buttons.add(bigButton(user, "Users"), constraints);
//
        mainPanel.add(invoiceTable);
        mainPanel.add(invoiceItemTable);
        mainPanel.add(equipmentTables);
        mainPanel.add(userTables);
        mainPanel.add(requestTable);
        mainPanel.add(transactionTable);
        mainPanel.add(buttons);
//
        cardLayout.addLayoutComponent(invoiceTable, "Invoice-Table");
        cardLayout.addLayoutComponent(requestTable, "Rental-Requests-Table");
        cardLayout.addLayoutComponent(transactionTable, "Transactions-Table");
        cardLayout.addLayoutComponent(invoiceTable, "Invoice-Table");
        cardLayout.addLayoutComponent(buttons, "Buttons");
        cardLayout.addLayoutComponent(invoiceItemTable, "Invoice-Items-Table");
        cardLayout.addLayoutComponent(userTables, "User-Tables");
        cardLayout.addLayoutComponent(equipmentTables, "Equipment-Tables");
        cardLayout.show(mainPanel, "Buttons");
    }

    private void createTabbedPane() {
        equipmentTables.addTab("Light Equipments", lightEquipmentTable);
        equipmentTables.addTab("Power Equipments", powerEquipmentTable);
        equipmentTables.addTab("Sound Equipments", soundEquipmentTable);
        equipmentTables.addTab("Stage Equipments", stageEquipmentTable);

        userTables.addTab("Customers", customerTable);
        userTables.addTab("Employees", employeeTable);
    }

    private JPanel bigButton(Button button, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(200, 250));
        panel.setBackground(theme.getCurrentScheme().getNeutralLight());

        JLabel lbl = new JLabel(label);
        lbl.setLabelFor(panel);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(18f));
        lbl.setForeground(theme.getCurrentScheme().getPrimary());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(button, constraints);
        constraints.insets = new Insets(20, 0, 0, 0);
        constraints.gridy = 1;
        panel.add(lbl, constraints);

        return panel;
    }

    @Override
    public void addListeners() {
        showTable(invoiceItems, "Invoice-Items-Table");
        showTable(user, "User-Tables");
        showTable(equipment, "Equipment-Tables");
        showTable(invoice, "Invoice-Table");
        showTable(transaction, "Transactions-Table");
        showTable(requests, "Rental-Requests-Table");

        closeTable(customerTable);
        closeTable(employeeTable);
        closeTable(stageEquipmentTable);
        closeTable(powerEquipmentTable);
        closeTable(lightEquipmentTable);
        closeTable(soundEquipmentTable);
        closeTable(invoiceItemTable);
        closeTable(requestTable);
        closeTable(transactionTable);
        closeTable(invoiceTable);
    }

    private void closeTable(TableFrame<?, ?> table) {
        table.addListener(new TableFrameListener() {
            @Override
            public void onClose() {
                cardLayout.show(mainPanel, "Buttons");
            }
        });
    }

    private void showTable(Button button, String name) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, name);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void setProperties() {
        mainPanel.setVisible(true);
    }
}
