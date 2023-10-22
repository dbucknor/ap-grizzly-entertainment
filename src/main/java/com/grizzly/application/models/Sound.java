package com.grizzly.application.models;

import java.util.Date;

public class Sound extends Equipment{
    private String wattage;
    private String inputVoltage;
    private String peakDb;
    private String ampType;

    public Sound() {
        super(); // Call the constructor of the superclass
        this.wattage = "";
        this.inputVoltage = "";
        this.peakDb = "";
        this.ampType = "";
    }

    public Sound(String id, String name, String description, String category, float price,
                 ArrayList<MaintainanceLog> maintainaceLogs, String type, Date nextAvaliableDate,
                 String wattage, String inputVoltage, String peakDb, String ampType) {
        super(id, name, description, category, price, maintainaceLogs, type, nextAvaliableDate);
        this.wattage = wattage;
        this.inputVoltage = inputVoltage;
        this.peakDb = peakDb;
        this.ampType = ampType;
    }

    public Sound(Sound sound) {
        super(sound); // Call the copy constructor of the superclass
        this.wattage = sound.wattage;
        this.inputVoltage = sound.inputVoltage;
        this.peakDb = sound.peakDb;
        this.ampType = sound.ampType;
    }
    
    public String getWattage() {
        return wattage;
    }

    public void setWattage(String wattage) {
        this.wattage = wattage;
    }

    public String getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(String inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public String getPeakDb() {
        return peakDb;
    }

    public void setPeakDb(String peakDb) {
        this.peakDb = peakDb;
    }

    public String getAmpType() {
        return ampType;
    }

    public void setAmpType(String ampType) {
        this.ampType = ampType;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "wattage='" + wattage + '\'' +
                ", inputVoltage='" + inputVoltage + '\'' +
                ", peakDb='" + peakDb + '\'' +
                ", ampType='" + ampType + '\'' +
                "} " + super.toString();
    }
}
