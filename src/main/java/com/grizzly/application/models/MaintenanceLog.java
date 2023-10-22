package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Date;

public class MaintenanceLog implements Serializable {
    private Date maintenanceDate;
    private String equipmentName;
    private String condition;
    private String details;


    public MaintenanceLog(Date maintenanceDate, String equipmentName, String condition, String details) {
        this.maintenanceDate = maintenanceDate;
        this.equipmentName = equipmentName;
        this.condition = condition;
        this.details = details;
    }


    public MaintenanceLog(Date maintenanceDate, String equipmentName, String condition) {
        this(maintenanceDate, equipmentName, condition, "");
    }


    public MaintenanceLog(MaintenanceLog other) {
        this(other.maintenanceDate, other.equipmentName, other.condition, other.details);
    }

    //toString
    public String toString() {
        return "MaintenanceLog{" +
                "maintenanceDate=" + maintenanceDate +
                ", equipmentName='" + equipmentName + '\'' +
                ", condition='" + condition + '\'' +
                ", details='" + details + '\'' +
                '}';
    }


    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getCondition() {
        return condition;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
