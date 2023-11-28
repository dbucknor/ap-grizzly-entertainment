package project.grizzly.application.views.customer;

import project.grizzly.application.controllers.CustomerScreenController;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.equipment.Equipment;
import project.grizzly.application.models.interfaces.FieldListeners;
import project.grizzly.application.models.interfaces.IView;
import project.grizzly.application.models.interfaces.TableUpdateListener;
import project.grizzly.application.views.components.CategoryPanel;
import project.grizzly.application.views.components.EquipmentBox;
import project.grizzly.application.views.components.fields.SearchBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerHome extends Box implements IView {

    EquipmentBox<Equipment> equipmentBox;
    private SearchBar searchBar;
    private Box categoriesPan;
    private CustomerScreenController controller;
    private HashMap<String, Equipment> categories;
    private JScrollPane scrollPane;
    private CategoryPanel light, power, stage, sound;

    public CustomerHome(CustomerScreenController controller) {
        super(BoxLayout.Y_AXIS);
        this.controller = controller;

        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }


    @Override
    public void initializeComponents() {
        searchBar = new SearchBar();
        categoriesPan = new Box(BoxLayout.Y_AXIS);
        categoriesPan.setAlignmentY(Box.TOP_ALIGNMENT);
        scrollPane = new JScrollPane(categoriesPan);
        scrollPane.setBackground(Color.WHITE);

        light = new CategoryPanel("Light", controller.getLight(), controller);
        stage = new CategoryPanel("Stage", controller.getStage(), controller);
        sound = new CategoryPanel("Sound", controller.getSound(), controller);
        power = new CategoryPanel("Power", controller.getPower(), controller);
    }

    @Override
    public void addComponents() {
        categoriesPan.add(light);
        categoriesPan.add(stage);
        categoriesPan.add(sound);
        categoriesPan.add(power);

        this.add(Box.createVerticalStrut(70));
        this.add(searchBar);
        this.add(Box.createVerticalStrut(70));
        this.add(scrollPane);
    }

    @Override
    public void addListeners() {
        searchBar.addListeners(new FieldListeners<String>() {
            @Override
            public void onChange(String fieldValue) {
                if (fieldValue == null || fieldValue.trim().isEmpty()) {
                    controller.setFilteredRecords(controller.getRecords());
                }
            }

            @Override
            public void onBlur(String fieldValue) {

            }
        });

        controller.addChangeListener(new TableUpdateListener() {
            @Override
            public void onTableUpdate(String[] titles, ArrayList<Object[]> tableData, List<FieldConfig> fieldConfigs) {
                controller.sort();

                light.setEquipments(controller.getLight());
                sound.setEquipments(controller.getSound());
                stage.setEquipments(controller.getStage());
                power.setEquipments(controller.getPower());
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
//        this.setBackground(Color.RED);
//        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
//        this.setOpaque(true);
        this.setVisible(true);
    }
}
