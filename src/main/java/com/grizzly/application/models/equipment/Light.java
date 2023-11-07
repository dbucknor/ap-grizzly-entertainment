package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.InvoiceItem;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Light")
@Table(name = "Light")
@Inheritance(strategy = InheritanceType.JOINED)
public class Light extends Equipment {
    @JoinColumn(name = "equipmentId", referencedColumnName = "equipmentId")
    private String equipmentId;
    private Integer luminosity;
    private Double wattage;
    private String voltage;

    public Light() {
        luminosity = 1;
        wattage = 0.0;
        voltage = "";
    }

    public Light(String id, String name, String description, String category, Double price,
                 ArrayList<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate, Integer luminosity,
                 Double wattage, String voltage, RentedPer rentedPer, RentalStatus rentalStatus) {
        super(id, name, description, category, price, maintenanceLogs, type, nextAvailableDate, rentedPer, rentalStatus);
        this.luminosity = luminosity;
        this.wattage = wattage;
        this.voltage = voltage;
    }

    public Light(Light light) {
        super(light.equipmentId, light.name, light.description, light.category, light.price, light.maintenanceLogs, light.type, light.nextAvailableDate, light.rentedPer, light.rentalStatus);
        this.luminosity = light.luminosity;
        this.wattage = light.wattage;
        this.voltage = light.voltage;
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

    public Integer getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(Integer luminosity) {
        this.luminosity = luminosity;
    }

    public void setLuminosity(Number luminosity) {
        this.luminosity = luminosity.intValue();
    }

    public Double getWattage() {
        return wattage;
    }

    public void setWattage(Double wattage) {
        this.wattage = wattage;
    }

    public void setWattage(Number wattage) {
        this.wattage = wattage.doubleValue();
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    @Override
    public String toString() {
        return "Light [lumens=" + luminosity + ", wattage=" + wattage + ", voltage=" + voltage + ", toString()="
                + super.toString() + "]";
    }

    @Transient
    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, category, price, type, luminosity, wattage, voltage, rentedPer, rentalStatus, nextAvailableDate};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Equipment Id", "Name", "Description", "Category", "Price", "Type", "Lumens", "Wattage", "Voltage", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    protected List<FieldConfig> configFields() {
        FieldConfig lumens = new FieldConfig(Double.class, "setLuminosity", "getLuminosity", "Luminosity:", FormFieldType.NUMBER, null, 0);
        lumens.addConstraint(new Constraint(Constraint.NOT_NULL, "Luminosity cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Luminosity must be greater than 0!"));
        super.configFields().add(6, lumens);

        FieldConfig watts = new FieldConfig(Double.class, "setWattage", "getWattage", "Wattage:", FormFieldType.NUMBER);
        watts.addConstraint(new Constraint(Constraint.NOT_NULL, "Wattage cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Wattage must be greater than 0!"));
        watts.setAllowsNegative(false);
        super.configFields().add(7, watts);

        FieldConfig volts = new FieldConfig(String.class, "setVoltage", "getVoltage", "Voltage:", FormFieldType.TEXT);
        volts.addConstraint(new Constraint(Constraint.NOT_NULL, "Voltage must not be empty!"));
        super.configFields().add(8, volts);

        return super.configFields();
    }
}
