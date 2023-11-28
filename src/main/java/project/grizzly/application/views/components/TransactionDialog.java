package project.grizzly.application.views.components;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.Transaction;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.services.AuthService;
import project.grizzly.application.services.CombinedQuery;
import project.grizzly.application.views.screens.MainWindow;
import project.grizzly.server.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class TransactionDialog extends JDialog implements IView {

    private TableController<Transaction, Integer> controller;
    private Box items;

    public TransactionDialog() {
        super(MainWindow.getInstance().getFrame(), "Transactions", true);
        controller = new TableController<>(Transaction.class, new Request("READ-WHERE", "TRANSACTION",
                new CombinedQuery<Transaction>("SELECT t FROM Transaction t").where("t.owner.userId",
                        "=:cid", AuthService.getInstance().getLoggedInUser().getUserId())));

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }


    @Override
    public void initializeComponents() {
        items = new Box(BoxLayout.Y_AXIS);
    }

    @Override
    public void addComponents() {
        addItems();
        this.add(items);
    }

    private void addItems() {
        items.removeAll();
        for (Transaction t : controller.getRecords()
        ) {
            items.add(listItem(t));
            items.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    @Override
    public void addListeners() {
        controller.addChangeListener(new TableUpdateListener() {
            @Override
            public void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
                addItems();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                controller.refreshData();
                super.componentShown(e);
            }
        });
    }

    @Override
    public void setProperties() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setMaximumSize(new Dimension(900, 700));

        this.setLocationRelativeTo(null);
//        this.setVisible(true);
    }

    private Box listItem(Transaction transaction) {
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        Box box2 = new Box(BoxLayout.Y_AXIS);
        JPanel panel = new JPanel(new GridLayout(1, 4));
        JLabel tid = new JLabel("Transaction Id: " + transaction.getTransactionId());
        JLabel paid = new JLabel("Amount Paid: " + transaction.getPaid());
        JLabel balance = new JLabel("Balance: " + transaction.getBalance());
        JLabel price = new JLabel("Price: " + transaction.getTotalPrice());
        panel.add(tid);
        panel.add(price);
        panel.add(paid);
        panel.add(balance);

        JLabel label = new JLabel("Items: ");
        box2.add(label);
        box2.add(Box.createRigidArea(new Dimension(0, 10)));
        for (InvoiceItem it :
                transaction.getRequest().getInvoice().getItems()) {
            box2.add(new JLabel(it.getEquipment().getName()));
        }

        box.add(panel);
        box.add(box2);
        return box;
    }
}
