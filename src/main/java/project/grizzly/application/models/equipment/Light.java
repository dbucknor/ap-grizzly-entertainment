package project.grizzly.application.models.equipment;

import project.grizzly.application.models.Constraint;
import project.grizzly.application.models.FieldConfig;
import project.grizzly.application.models.TableConfig;
import project.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "Light")
@Table(name = "Light")
@PrimaryKeyJoinColumn(name = "equipmentId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Light extends Equipment {
    private Integer luminosity;
    private Double wattage;
    private String voltage;

    public Light() {
        luminosity = 1;
        wattage = 0.0;
        voltage = "";
    }

    public Light(Equipment details, Integer luminosity, Double wattage, String voltage) {
        super(details);
        this.luminosity = luminosity;
        this.wattage = wattage;
        this.voltage = voltage;
    }

    public Light(Light light) {
        super(light);
        this.luminosity = light.luminosity;
        this.wattage = light.wattage;
        this.voltage = light.voltage;
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
        return new Object[]{equipmentId, name, description, condition, category, price, type, luminosity, wattage, voltage, rentedPer, rentalStatus, nextAvailableDate.format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a"))};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"Equipment Id", "Name", "Description", "Condition", "Category", "Price", "Type", "Lumens", "Wattage", "Voltage", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = super.configFields();

        FieldConfig lumens = new FieldConfig(Double.class, "setLuminosity", "getLuminosity", "Luminosity:", FormFieldType.NUMBER, null, 0);
        lumens.addConstraint(new Constraint(Constraint.NOT_NULL, "Luminosity cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Luminosity must be greater than 0!"));
        fcs.add(6, lumens);

        FieldConfig watts = new FieldConfig(Double.class, "setWattage", "getWattage", "Wattage:", FormFieldType.NUMBER);
        watts.addConstraint(new Constraint(Constraint.NOT_NULL, "Wattage cannot be empty!"))
                .addConstraint(new Constraint(Constraint.GREATER, 0, "Wattage must be greater than 0!"));
        watts.setAllowsNegative(false);
        fcs.add(7, watts);

        FieldConfig volts = new FieldConfig(String.class, "setVoltage", "getVoltage", "Voltage:", FormFieldType.TEXT);
        volts.addConstraint(new Constraint(Constraint.NOT_NULL, "Voltage must not be empty!"));
        fcs.add(8, volts);

        return fcs;
    }
}
