package project.grizzly.application.views.employee;

import project.grizzly.application.controllers.TableController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.RentalRequest;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.components.RequestBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class RentalRequests extends JPanel implements IView {
    private TableController<RentalRequest, Integer> controller;
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

        for (RentalRequest rr : controller.getRecords()
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
                int rows = controller.getRecords().size() / 2;
                ((GridLayout) requests.getLayout()).setRows(rows);

                for (RentalRequest rr : controller.getRecords()
                ) {
                    requests.add(new RequestBox(rr, controller));
                }
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
//        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
}
