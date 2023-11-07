package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.enums.FuelSource;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Power")
@Table(name = "Power")
@Inheritance(strategy = InheritanceType.JOINED)
public class Power extends Equipment {
    @JoinColumn(name = "equipmentId", referencedColumnName = "equipmentId")
    private String equipmentId;
    private Double outputPower;
    private Integer phase;
    private String outputVoltage;
    private FuelSource fuelSource;

    public Power() {
        outputPower = 0.0;
        phase = 1;
        outputVoltage = "";
        equipmentId = null;
        fuelSource = null;
    }

    public Power(String equipmentId, String name, String description, String category, Double price,
                 ArrayList<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate,
                 Double outputPower, Integer phase, String outputVoltage, FuelSource fuelSource, RentedPer rentedPer, RentalStatus rentalStatus) {
        super(equipmentId, name, description, category, price, maintenanceLogs, type, nextAvailableDate, rentedPer, rentalStatus);
        this.outputPower = outputPower;
        this.phase = phase;
        this.outputVoltage = outputVoltage;
        this.fuelSource = fuelSource;
        this.equipmentId = equipmentId;

    }


    // Copy constructor
    public Power(Power power) {
        super(power); // Calling the superclass's copy constructor
        this.outputPower = power.outputPower;
        this.phase = power.phase;
        this.outputVoltage = power.outputVoltage;
        this.fuelSource = power.fuelSource;
        this.equipmentId = power.equipmentId;
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

    public Double getOutputPower() {
        return outputPower;
    }

    public void setOutputPower(Double outputPower) {
        this.outputPower = outputPower;
    }

    public void setOutputPower(Number outputPower) {
        this.outputPower = outputPower.doubleValue();
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public void setPhase(Number phase) {
        this.phase = phase.intValue();
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public FuelSource getFuelSource() {
        return fuelSource;
    }

    public void setFuelSource(FuelSource fuelSource) {
        this.fuelSource = fuelSource;
    }

    @Override
    public String toString() {
        return "Power{" +
                "id='" + equipmentId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' + ", type='" + type + '\'' +
                ", outputPower='" + outputPower + '\'' +
                ", phase='" + phase + '\'' +
                ", outputVoltage='" + outputVoltage + '\'' +
                ", fuelSource=" + fuelSource +
                ", price=" + price +
                ", rentedPer=" + rentedPer +
                ", nextAvailableDate=" + nextAvailableDate +
                ", maintenanceLogs=" + maintenanceLogs +
                '}';
    }

    @Transient
    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, category, price, type, outputPower, outputVoltage, phase, fuelSource, rentedPer, rentalStatus, nextAvailableDate};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Category", "Price", "Type", "Output Power", "Output Voltage", "Phase", "Fuel Source", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    protected List<FieldConfig> configFields() {
        FieldConfig power = new FieldConfig(Double.class, "setOutputPower", "getOutputPower", "Output Power:", FormFieldType.NUMBER);
        power.addConstraint(new Constraint(Constraint.NOT_NULL, "Output power cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Luminosity must be greater than 0!"));
        power.setAllowsNegative(false);
        super.configFields().add(6, power);

        FieldConfig watts = new FieldConfig(Double.class, "setOutputVoltage", "getOutputVoltage", "Wattage:", FormFieldType.TEXT);
        watts.addConstraint(new Constraint(Constraint.NOT_NULL, "Output Voltage cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Wattage must be greater than 0!"));
        watts.setAllowsNegative(false);
        super.configFields().add(7, watts);

        FieldConfig volts = new FieldConfig(Integer.class, "setPhase", "getPhase", "Phase:", FormFieldType.NUMBER);
        volts.addConstraint(new Constraint(Constraint.NOT_NULL, "Phase must not be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Wattage must be greater than 0!"));
        volts.setAllowsNegative(false);
        super.configFields().add(8, volts);

        FieldConfig fSource = new FieldConfig(String.class, "setFuelSource", "getFuelSource", "Fuel Source:", FormFieldType.SELECT, FuelSource.values());
        super.configFields().add(9, fSource);

        return super.configFields();
    }
}
