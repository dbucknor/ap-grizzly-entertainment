package com.grizzly.application.views.employee;

import com.grizzly.application.controllers.TableController;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.RentalRequest;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.models.interfaces.TableUpdateListener;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.components.RequestBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRequests extends JPanel implements IView {
    private TableController<RentalRequest, String> controller;
    JScrollPane scrollPane;
    JLabel label;
    JPanel requests, mainPanel;
    private ThemeManager theme;

    public RentalRequests() {
        this.controller = new TableController<>(RentalRequest.class);
        theme = ThemeManager.getInstance();

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }


    @Override
    public void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout());
        label = new JLabel("Rental Requests");
        label.setFont(theme.getFontLoader().getH2());
        requests = new JPanel(new GridLayout(1, 2));

        for (RentalRequest rr : controller.getAllRecords()
        ) {
            requests.add(new RequestBox(rr, controller));
        }

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(requests, BorderLayout.CENTER);
        scrollPane = new JScrollPane(mainPanel);
    }

    @Override
    public void addComponents() {
        this.add(scrollPane);
    }

    @Override
    public void addListeners() {
        controller.addChangeListener(new TableUpdateListener() {
            @Override
            public void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
                int rows = controller.getAllRecords().size() / 2;
                ((GridLayout) requests.getLayout()).setRows(rows);

                for (RentalRequest rr : controller.getAllRecords()
                ) {
                    requests.add(new RequestBox(rr, controller));
                }
            }
        });
    }

    @Override
    public void setProperties() {
//        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
}
