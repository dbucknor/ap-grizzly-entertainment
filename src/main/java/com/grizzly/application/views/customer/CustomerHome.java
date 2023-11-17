package com.grizzly.application.views.customer;

import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.equipment.Equipment;
import com.grizzly.application.models.interfaces.IView;
import com.grizzly.application.views.components.EquipmentBox;
import com.grizzly.application.views.screens.Screen;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CustomerHome extends JPanel implements IView {

    EquipmentBox<Equipment> equipmentBox;

    public CustomerHome() {
        super(new FlowLayout(FlowLayout.CENTER));
        initializeComponents();
        addComponents();
        addListeners();
        setProperties();
    }

    @Override
    public void initializeComponents() {
//        equipmentBox = new EquipmentBox<>(new Equipment("kk42134", "Twest Equipment 123 jkhjkf 3889jkc wkk", "A very short escripntion tyhta u how would be long enought yea!!", "Any", 197913.0, "any", LocalDateTime.now(), RentedPer.DAY, RentalStatus.AVAILABLE));

    }

    @Override
    public void addComponents() {
//        this.add(equipmentBox);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setProperties() {
//        this.setBackground(Color.RED);
//        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
//        this.setOpaque(true);
        this.setVisible(true);
    }
}
