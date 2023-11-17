package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.enums.Condition;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Stage")
@Table(name = "Stage")
@PrimaryKeyJoinColumn(name = "equipmentId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Stage extends Equipment {
    private Double width;
    private Double height;
    private Double length;

    public Stage() {
        this.width = 0.0;
        this.height = 0.0;
        this.length = 0.0;
    }

    public Stage(Equipment details, Double width, Double height, Double length) {
        super(details);
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Stage(Stage stage) {
        super(stage);
        this.width = stage.width;
        this.height = stage.height;
        this.length = stage.length;
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
        return new String[]{"Id", "Name", "Description", "Condition", "Category", "Price ($)", "Type", "Width (ft)", "Height (ft)", "Length (ft)", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Transient

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, condition, category, price, type, width, height, length, rentedPer, rentalStatus, nextAvailableDate};
    }

    @Transient


    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = super.configFields();

        FieldConfig w = new FieldConfig(Double.class, "setWidth", "getWidth", "Width (ft):", FormFieldType.NUMBER, null, 4.0);
        w.addConstraint(new Constraint(Constraint.GREATER, 0, "Width must be greater than o!"));
        w.setAllowsNegative(false);
        fcs.add(6, w);

        FieldConfig h = new FieldConfig(Double.class, "setHeight", "getHeight()", "Height (ft):", FormFieldType.NUMBER, null, 4.0);
        h.addConstraint(new Constraint(Constraint.GREATER, 0, "Height must be greater than o!"));
        h.setAllowsNegative(false);
        fcs.add(7, h);

        FieldConfig l = new FieldConfig(Double.class, "setLength", "getLength", "Length (ft):", FormFieldType.NUMBER, null, 4.0);
        l.addConstraint(new Constraint(Constraint.GREATER, 0, "Length must be greater than o!"));
        l.setAllowsNegative(false);
        fcs.add(8, l);

        return fcs;
    }
}
