package project.grizzly.application.models.equipment;

import project.grizzly.application.models.Constraint;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.TableConfig;
import project.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "Sound")
@Table(name = "Sound")
@PrimaryKeyJoinColumn(name = "equipmentId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Sound extends Equipment {
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

    public Sound(Equipment details, Double wattage, String inputVoltage, Double peakDecibel, String amplifierClass) {
        super(details);
        this.wattage = wattage;
        this.inputVoltage = inputVoltage;
        this.peakDecibel = peakDecibel;
        this.amplifierClass = amplifierClass;
    }

    public Sound(Sound sound) {
        super(sound); // Call the copy constructor of the superclass
        this.wattage = sound.wattage;
        this.inputVoltage = sound.inputVoltage;
        this.peakDecibel = sound.peakDecibel;
        this.amplifierClass = sound.amplifierClass;
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
                "wattage=" + wattage +
                ", inputVoltage='" + inputVoltage + '\'' +
                ", peakDecibel=" + peakDecibel +
                ", amplifierClass='" + amplifierClass + '\'' +
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
    public String[] getTableTitles() {
        return new String[]{"Equipment Id", "Name", "Description", "Condition", "Price", "Category", "Type", "Power", "Input Voltage", "Peak Db", "Amplifier Class", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Transient

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, condition, price, category, type, wattage, inputVoltage, peakDecibel, amplifierClass, rentedPer, rentalStatus, nextAvailableDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))};
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
