package com.grizzly.application.models;

import com.grizzly.application.models.enums.Condition;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "MaintenanceLog")
@Table(name = "MaintenanceLog")
public class MaintenanceLog implements Serializable {
    @Id
    @Column(name = "timestamp")
    private String timestamp;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "equipmentId")
    private String equipmentId;
    @Column(name = "condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @Column(name = "details")
    private String details;

    public MaintenanceLog() {
        this.timestamp = "0";
        this.date = null;
        this.equipmentId = null;
        this.condition = null;
        this.details = null;
    }

    public MaintenanceLog(String timestamp, LocalDateTime date, String equipmentName, Condition condition, String details) {
        this.timestamp = timestamp;
        this.date = date;
        this.equipmentId = equipmentName;
        this.condition = condition;
        this.details = details;
    }

    public MaintenanceLog(MaintenanceLog other) {
        this.timestamp = other.timestamp;
        this.date = other.date;
        this.equipmentId = other.equipmentId;
        this.condition = other.condition;
        this.details = other.details;
    }

    @Override
    public String toString() {
        return "MaintenanceLog{" +
                "timestamp='" + timestamp + '\'' +
                ", maintenanceDate=" + date +
                ", equipmentName='" + equipmentId + '\'' +
                ", condition=" + condition +
                ", details='" + details + '\'' +
                '}';
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime maintenanceDate) {
        this.date = maintenanceDate;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
