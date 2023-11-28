package project.grizzly.application.views.components;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.RentalRequest;
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

public class UserRequests extends JDialog implements IView {

    private TableController<RentalRequest, Integer> controller;
    private Box items;

    public UserRequests() {
        super(MainWindow.getInstance().getFrame(), "Requests", true);
        controller = new TableController<>(RentalRequest.class, new Request("READ-WHERE", "RENTALREQUEST",
                new CombinedQuery<RentalRequest>("SELECT r FROM RentalRequest r").where("r.requestFrom.userId",
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
        for (RentalRequest request : controller.getRecords()
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
    }
}
