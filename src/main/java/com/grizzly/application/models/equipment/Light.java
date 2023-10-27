package com.grizzly.application.models.equipment;

import com.grizzly.application.models.MaintenanceLog;
import com.grizzly.application.models.enums.RentedPer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Light extends Equipment {
    private String lumens;
    private String wattage;
    private String voltage;

    public Light() {
        lumens = "";
        wattage = "";
        voltage = "";
    }

    public Light(String id, String name, String description, String category, float price,
                 ArrayList<MaintenanceLog> maintenanceLogs, String type, LocalDateTime nextAvaliableDate, String lumens,
                 String wattage, String voltage, RentedPer rentedPer) {
        super(id, name, description, category, price, maintenanceLogs, type, nextAvaliableDate, rentedPer);
        this.lumens = lumens;
        this.wattage = wattage;
        this.voltage = voltage;
    }

    public Light(Light light) {
        super(light.id, light.name, light.description, light.category, light.price, light.maintenanceLogs, light.type, light.nextAvailableDate, light.rentedPer);
        this.lumens = light.lumens;
        this.wattage = light.wattage;
        this.voltage = light.voltage;
    }

    public String getLumens() {
        return lumens;
    }

    public void setLumens(String lumens) {
        this.lumens = lumens;
    }

    public String getWattage() {
        return wattage;
    }

    public void setWattage(String wattage) {
        this.wattage = wattage;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    @Override
    public String toString() {
        return "Light [lumens=" + lumens + ", wattage=" + wattage + ", voltage=" + voltage + ", toString()="
                + super.toString() + "]";
    }

}
