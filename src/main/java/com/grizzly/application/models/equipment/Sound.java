package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Sound")
@Table(name = "Sound")
@Inheritance(strategy = InheritanceType.JOINED)
public class Sound extends Equipment {
    @JoinColumn(name = "equipmentId", referencedColumnName = "equipmentId")
    private String equipmentId;
    private Double wattage;
    private String inputVoltage;
    private Double peakDecibel;
    private String amplifierClass;

    public Sound() {
        super(); // Call the constructor of the superclass
        this.wattage = 0.0;
        this.inputVoltage = "";
        this.peakDecibel = 0.0;
        this.amplifierClass = "";
    }

    public Sound(String equipmentId, String name, String description, String category, Double price,
                 ArrayList<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate,
                 Double wattage, String inputVoltage, Double peakDecibel, String amplifierClass, RentedPer rentedPer, RentalStatus rentalStatus) {
        super(equipmentId, name, description, category, price, maintenanceLogs, type, nextAvailableDate, rentedPer, rentalStatus);
        this.wattage = wattage;
        this.inputVoltage = inputVoltage;
        this.peakDecibel = peakDecibel;
        this.amplifierClass = amplifierClass;
        this.equipmentId = equipmentId;
    }

    public Sound(Sound sound) {
        super(sound); // Call the copy constructor of the superclass
        this.wattage = sound.wattage;
        this.inputVoltage = sound.inputVoltage;
        this.peakDecibel = sound.peakDecibel;
        this.amplifierClass = sound.amplifierClass;
        this.equipmentId = sound.equipmentId;
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

    public Double getWattage() {
        return wattage;
    }

    public void setWattage(Double wattage) {
        this.wattage = wattage;
    }

    public void setWattage(Number wattage) {
        this.wattage = wattage.doubleValue();
    }

    public String getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(String inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public Double getPeakDecibel() {
        return peakDecibel;
    }

    public void setPeakDecibel(Double peakDecibel) {
        this.peakDecibel = peakDecibel;
    }

    public void setPeakDecibel(Number peakDecibel) {
        this.peakDecibel = peakDecibel.doubleValue();
    }

    public String getAmplifierClass() {
        return amplifierClass;
    }

    public void setAmplifierClass(String amplifierClass) {
        this.amplifierClass = amplifierClass;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "equipmentId='" + equipmentId + '\'' +
                ", wattage=" + wattage +
                ", inputVoltage='" + inputVoltage + '\'' +
                ", peakDecibel=" + peakDecibel +
                ", amplifierClass='" + amplifierClass + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", rentalStatus=" + rentalStatus +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", rentedPer=" + rentedPer +
                ", maintenanceLogs=" + maintenanceLogs +
                ", type='" + type + '\'' +
                ", nextAvailableDate=" + nextAvailableDate +
                '}';
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Price", "Category", "Type", "Power", "Input Voltage", "Peak Db", "Amplifier Class", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Transient

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, price, category, type, wattage, inputVoltage, peakDecibel, amplifierClass, rentedPer, rentalStatus, nextAvailableDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))};
    }


    @Transient
    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(this.getTableTitles(), this.configFields());
    }

    @Transient
    @Override
    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = super.configFields();

        FieldConfig power = new FieldConfig(Double.class, "setWattage", "getWattage", "Power:", FormFieldType.NUMBER);
        power.addConstraint(new Constraint(Constraint.GREATER, 0, "Power must be greater than 0!"));
        fcs.add(6, power);

        FieldConfig volts = new FieldConfig(String.class, "setInputVoltage", "getInputVoltage", "Voltage:", FormFieldType.TEXT);
        volts.addConstraint(new Constraint(Constraint.NOT_NULL, "Type cannot be empty!"));
        fcs.add(7, volts);

        FieldConfig db = new FieldConfig(Double.class, "setPeakDecibel", "getPeakDecibel", "Peak Db:", FormFieldType.NUMBER);
        power.addConstraint(new Constraint(Constraint.GREATER, 0, "Power must be greater than 0!"));
        fcs.add(8, db);

        FieldConfig amp = new FieldConfig(String.class, "setAmplifierClass", "getAmplifierClass", "Amplifier Class:", FormFieldType.TEXT);
        amp.addConstraint(new Constraint(Constraint.NOT_NULL, "Amplifier class cannot be empty!"));
        fcs.add(9, amp);

        return fcs;
    }
}
