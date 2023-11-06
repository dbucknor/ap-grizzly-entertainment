package com.grizzly.application.models.equipment;

import com.grizzly.application.models.Constraint;
import com.grizzly.application.models.FieldConfig;
import com.grizzly.application.models.TableConfig;
import com.grizzly.application.models.interfaces.ITableEntity;
import com.grizzly.application.models.enums.Condition;
import com.grizzly.application.models.enums.RentalStatus;
import com.grizzly.application.models.enums.RentedPer;
import com.grizzly.application.models.enums.FormFieldType;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Equipment")
@Table(name = "Equipment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Equipment implements Serializable, ITableEntity {
    @Id
    @Column(name = "equipmentId")
    protected String equipmentId;

    @Column(name = "name")
    protected String name;

    @Column(name = "image")
    @Transient
    protected ImageIcon image;
    @Column(name = "description")
    protected String description;

    @Enumerated(EnumType.STRING)
    protected RentalStatus rentalStatus;
    @Column(name = "category")
    protected String category;
    @Column(name = "price")
    protected Double price;
    @Column(name = "rentedPer")
    @Enumerated(EnumType.STRING)
    protected RentedPer rentedPer;
    @Transient
    protected List<MaintenanceLog> maintenanceLogs;
    @Column(name = "type")
    protected String type;
    @Column(name = "nextAvailableDate")
    protected LocalDateTime nextAvailableDate;

    public Equipment() {
        equipmentId = "";
        name = "";
        description = "";
        category = "";
        price = 0.0;
        type = "";
        nextAvailableDate = LocalDateTime.now();
        rentedPer = RentedPer.DAY;
        rentalStatus = RentalStatus.AVAILABLE;
    }

    public Equipment(String equipmentId, String name, String description, String category, Double price,
                     List<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate, RentedPer rentedPer, RentalStatus rentalStatus) {
        this.equipmentId = equipmentId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.maintenanceLogs = maintenanceLogs;
        this.type = type;
        this.nextAvailableDate = nextAvailableDate;
        this.rentedPer = rentedPer;
        this.rentalStatus = rentalStatus;


    }

    public Equipment(Equipment other) {
        this.equipmentId = other.equipmentId;
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.price = other.price;
        this.maintenanceLogs = new ArrayList<>(other.maintenanceLogs); // Copy the list
        this.type = other.type;
        this.nextAvailableDate = other.nextAvailableDate;
        this.rentedPer = other.rentedPer;
        this.rentalStatus = other.rentalStatus;
    }


    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String id) {
        this.equipmentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPrice(Number price) {
        this.price = price.doubleValue();
    }

    public List<MaintenanceLog> getMaintenanceLogs() {
        return maintenanceLogs;
    }

    public void setMaintenanceLogs(List<MaintenanceLog> maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getNextAvailableDate() {
        return nextAvailableDate;
    }

    public void setNextAvailableDate(LocalDateTime nextAvailableDate) {
        this.nextAvailableDate = nextAvailableDate;
    }

    public MaintenanceLog getLastMaintenanceLog() {
        return maintenanceLogs.get(maintenanceLogs.size() - 1);
    }

    public Condition getCondition() {
        return getLastMaintenanceLog().getCondition();
    }

    public RentedPer getRentedPer() {
        return rentedPer;
    }

    public void setRentedPer(RentedPer rentedPer) {
        this.rentedPer = rentedPer;
    }

    public void setRentedPer(String rentedPer) {
        this.rentedPer = RentedPer.valueOf(rentedPer);
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = RentalStatus.valueOf(rentalStatus);
    }


    @Override
    public String toString() {
        return "Equipment{" +
                "id='" + equipmentId + '\'' +
                ", name='" + name + '\'' +
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

    private void cfgTable() {

    }

    @Override
    public Object[] getValues() {
        return new Object[]{equipmentId, name, description, category, price, type, rentedPer, rentalStatus, nextAvailableDate};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Id", "Name", "Description", "Category", "Price", "Type", "Rented Per", "Rental Status", "Next Available Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {

        List<FieldConfig> fcs = configFields();

        return new TableConfig(getTableTitles(), null, fcs);
    }

    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig id = new FieldConfig(String.class, "setEquipmentId", "getEquipmentId", "Id", FormFieldType.TEXT, true);
        id.addConstraint(new Constraint(Constraint.NOT_NULL, "Id cannot be empty!"));
        fcs.add(id);

        FieldConfig name = new FieldConfig(String.class, "setName", "getName", "Name:", FormFieldType.TEXT);
        name.addConstraint(new Constraint(Constraint.NOT_NULL, "Name cannot be empty!"));
        fcs.add(name);

        FieldConfig description = new FieldConfig(String.class, "setDescription", "getDescription", "Description:", FormFieldType.LONGTEXT, 350.0, 0.0);
        fcs.add(description);

        FieldConfig category = new FieldConfig(String.class, "setCategory", "getCategory", "Category:", FormFieldType.TEXT);
        fcs.add(category);

        FieldConfig price = new FieldConfig(Double.class, "setPrice", "getPrice", "Price:", FormFieldType.NUMBER, null, 0.0);
        price.addConstraint(new Constraint(Constraint.GREATER, 0, "Value must be greater than 0!"));
        fcs.add(price);

        FieldConfig type = new FieldConfig(String.class, "setType", "getType", "Type:", FormFieldType.TEXT);
        type.addConstraint(new Constraint(Constraint.NOT_NULL, "Type cannot be empty!"));
        fcs.add(type);

        FieldConfig rentedPer = new FieldConfig(RentedPer.class, "setRentedPer", "getRentedPer", "Rented Per:", FormFieldType.SELECT, RentedPer.values());
        fcs.add(rentedPer);

        FieldConfig status = new FieldConfig(RentalStatus.class, "setRentalStatus", "getRentalStatus", "Rental Status:", FormFieldType.SELECT, RentalStatus.values());
        fcs.add(status);

        FieldConfig nextAvailable = new FieldConfig(String.class, "setNextAvailableDate", "getNextAvailableDate", "Next Available Date:", FormFieldType.DATE);
        nextAvailable.addConstraint(new Constraint(Constraint.NOT_NULL, "Type cannot be empty!"));
        fcs.add(nextAvailable);

        return fcs;
    }
}