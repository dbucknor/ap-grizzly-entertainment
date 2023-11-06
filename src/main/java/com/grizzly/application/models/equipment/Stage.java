package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Stage")
@Table(name = "Stage")
@Inheritance(strategy = InheritanceType.JOINED)
public class Stage extends Equipment {
    @JoinColumn(name = "equipmentId", referencedColumnName = "equipmentId")
    private String equipmentId;
    private Double width;
    private Double height;
    private Double length;

    public Stage() {
        this.width = 0.0;
        this.height = 0.0;
        this.length = 0.0;
    }

    public Stage(String equipmentId, String name, String description, String category, Double price,
                 ArrayList<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate,
                 Double width, Double height, Double length, RentedPer rentedPer, RentalStatus rentalStatus) {
        super(equipmentId, name, description, category, price, maintenanceLogs, type, nextAvailableDate, rentedPer, rentalStatus);
        this.width = width;
        this.height = height;
        this.length = length;
        this.equipmentId = equipmentId;

    }

    public Stage(Stage stage) {
        super(stage);
        this.width = stage.width;
        this.height = stage.height;
        this.length = stage.length;
        this.equipmentId = stage.equipmentId;

    }

    @Override
    public String getEquipmentId() {
        return equipmentId;
    }

    @Override
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
        super.setEquipmentId(equipmentId);
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setWidth(Number width) {
        this.width = width.doubleValue();
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setHeight(Number height) {
        this.height = height.doubleValue();
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public void setLength(Number length) {
        this.length = length.doubleValue();
    }


    @Override
    public String toString() {
        return "Stage{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                "} " + super.toString();
    }

    @Transient

    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Category", "Price ($)", "Type", "Width (ft)", "Height (ft)", "Length (ft)", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Transient

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, category, price, type, width, height, length, rentedPer, rentalStatus, nextAvailableDate};
    }

    @Transient


    protected List<FieldConfig> configFields() {


        FieldConfig w = new FieldConfig(Double.class, "setWidth", "getWidth", "Width (ft):", FormFieldType.NUMBER, null, 4.0);
        w.addConstraint(new Constraint(Constraint.GREATER, 0, "Width must be greater than o!"));
        w.setAllowsNegative(false);
        super.configFields().add(6, w);

        FieldConfig h = new FieldConfig(Double.class, "setHeight", "getHeight()", "Height (ft):", FormFieldType.NUMBER, null, 4.0);
        h.addConstraint(new Constraint(Constraint.GREATER, 0, "Height must be greater than o!"));
        h.setAllowsNegative(false);
        super.configFields().add(7, h);

        FieldConfig l = new FieldConfig(Double.class, "setLength", "getLength", "Length (ft):", FormFieldType.NUMBER, null, 4.0);
        l.addConstraint(new Constraint(Constraint.GREATER, 0, "Length must be greater than o!"));
        l.setAllowsNegative(false);
        super.configFields().add(8, l);

        return super.configFields();
    }
}
