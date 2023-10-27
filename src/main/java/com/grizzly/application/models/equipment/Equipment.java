package com.grizzly.application.models.equipment;

import com.grizzly.application.models.MaintenanceLog;
import com.grizzly.application.models.enums.Condition;
import com.grizzly.application.models.enums.RentedPer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Equipment")
@Table(name = "equipment")
public class Equipment implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    protected String id;

    @Column(name = "name")
    protected String name;
    @Column(name = "description")
    protected String description;
    @Column(name = "category")
    protected String category;
    @Column(name = "price")
    protected float price;
    @Column(name = "rentedPer")
    @Enumerated(EnumType.STRING)
    protected RentedPer rentedPer;
    @OneToMany
    protected List<MaintenanceLog> maintenanceLogs;
    @Column(name = "type")
    protected String type;
    @Column(name = "nextAvailableDate")
    protected LocalDateTime nextAvailableDate;

    public Equipment() {
        id = "";
        name = "";
        description = "";
        category = "";
        price = 0.0f;
        type = "";
        nextAvailableDate = LocalDateTime.now();
        rentedPer = RentedPer.DAY;
    }

    public Equipment(String id, String name, String description, String category, float price,
                     List<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvailableDate, RentedPer rentedPer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.maintenanceLogs = maintenanceLogs;
        this.type = type;
        this.nextAvailableDate = nextAvailableDate;
        this.rentedPer = rentedPer;
    }

    public Equipment(Equipment other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.price = other.price;
        this.maintenanceLogs = new ArrayList<>(other.maintenanceLogs); // Copy the list
        this.type = other.type;
        this.nextAvailableDate = other.nextAvailableDate;
        this.rentedPer = other.rentedPer;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public void setNextAvailableDate(LocalDateTime nextAvaliableDate) {
        this.nextAvailableDate = nextAvaliableDate;
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

    @Override
    public String toString() {
        return "Equipment [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
                + ", price=" + price + ", rentedPer=" + rentedPer + ", type=" + type + ", nextAvailableDate=" + nextAvailableDate + "]";
    }


}