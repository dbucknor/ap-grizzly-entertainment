package project.grizzly.application.views.components;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.InvoiceItem;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.Transaction;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.views.screens.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserRequests extends JDialog implements IView {

    private TableController<RentalRequest, Integer> controller;
    private Box items;

    public UserRequests() {
        super(MainWindow.getInstance().getFrame(), "Requests", true);
        controller = new TableController<>(RentalRequest.class);

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
        for (RentalRequest request : controller.getAllRecords()
        ) {
            items.add(new RequestBox(request, controller));
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
