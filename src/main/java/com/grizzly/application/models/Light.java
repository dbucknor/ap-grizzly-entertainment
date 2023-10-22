package com.grizzly.application.models;

import java.util.ArrayList;
import java.util.Date;

public class Light extends Equipment{
	private String lumens;
	private String wattage;
	private String voltage;
	
	public Light() {
		super();
		lumens = "";
		wattage = "";
		voltage = "";
	}

	public Light(String id, String name, String description, String category, float price,
			ArrayList<MaintainanceLog> maintainaceLogs, String type, Date nextAvaliableDate, String lumens,
			String wattage, String voltage) {
		super(id, name, description, category, price, maintainaceLogs, type, nextAvaliableDate);
		this.lumens = lumens;
		this.wattage = wattage;
		this.voltage = voltage;
	}

	public Light(Light light) {
		super(light.getId(), light.getName(), light.getDescription(), light.getCategory(), light.getPrice(), light.getMaintainaceLogs());
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
