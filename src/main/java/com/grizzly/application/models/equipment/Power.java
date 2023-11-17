package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.enums.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Power")
@Table(name = "Power")
@PrimaryKeyJoinColumn(name = "equipmentId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Power extends Equipment {
    private Double outputPower;
    private Integer phase;
    private String outputVoltage;
    private FuelSource fuelSource;

    public Power() {
        outputPower = 0.0;
        phase = 1;
        outputVoltage = "";
        fuelSource = null;
    }

    public Power(Equipment details, Integer phase, String outputVoltage, Double outputPower, FuelSource fuelSource) {
        super(details);
        this.outputPower = outputPower;
        this.phase = phase;
        this.outputVoltage = outputVoltage;
        this.fuelSource = fuelSource;

    }


    // Copy constructor
    public Power(Power power) {
        super(power); // Calling the superclass's copy constructor
        this.outputPower = power.outputPower;
        this.phase = power.phase;
        this.outputVoltage = power.outputVoltage;
        this.fuelSource = power.fuelSource;
    }

    @Override
    public String getEquipmentId() {
        return equipmentId;
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
                "outputPower=" + outputPower +
                ", phase=" + phase +
                ", outputVoltage='" + outputVoltage + '\'' +
                ", fuelSource=" + fuelSource +
                ", equipmentId='" + equipmentId + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", rentalStatus=" + rentalStatus +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", rentedPer=" + rentedPer +
                ", condition=" + condition +
                ", type='" + type + '\'' +
                ", nextAvailableDate=" + nextAvailableDate +
                '}';
    }

    @Transient
    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, condition, category, price, type, outputPower, outputVoltage, phase, fuelSource, rentedPer, rentalStatus, nextAvailableDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Condition", "Category", "Price", "Type", "Output Power", "Output Voltage", "Phase", "Fuel Source", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = super.configFields();

        FieldConfig power = new FieldConfig(Double.class, "setOutputPower", "getOutputPower", "Output Power:", FormFieldType.NUMBER);
        power.addConstraint(new Constraint(Constraint.NOT_NULL, "Output power cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Luminosity must be greater than 0!"));
        power.setAllowsNegative(false);
        fcs.add(6, power);

        FieldConfig watts = new FieldConfig(String.class, "setOutputVoltage", "getOutputVoltage", "Output Voltage:", FormFieldType.TEXT);
        watts.addConstraint(new Constraint(Constraint.NOT_NULL, "Output Voltage cannot be empty!"));
        fcs.add(7, watts);

        FieldConfig volts = new FieldConfig(Integer.class, "setPhase", "getPhase", "Phase:", FormFieldType.NUMBER);
        volts.addConstraint(new Constraint(Constraint.NOT_NULL, "Phase must not be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Wattage must be greater than 0!"));
        volts.setAllowsNegative(false);
        fcs.add(8, volts);

        FieldConfig fSource = new FieldConfig(FuelSource.class, "setFuelSource", "getFuelSource", "Fuel Source:", FormFieldType.SELECT, FuelSource.values());
        fcs.add(9, fSource);

        return fcs;
    }
}
