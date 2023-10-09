package com.grizzly.application.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.grizzly.application.enums.PricingPeriod;
import com.grizzly.application.enums.RentalStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Light")
public class Light extends Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lumens;
	private String wattage;
	private String voltage;

	// Primary contructor
	public Light(String id, String name, String description, String category, float price, PricingPeriod pricingPeriod,
			List<MaintenanceLog> maintenanceLogs, RentalStatus rentalStatus, String type, Date nextAvailableDate,
			String lumens, String wattage, String voltage) {
		super(id, name, description, category, price, pricingPeriod, maintenanceLogs, rentalStatus, type,
				nextAvailableDate);
		populateLightVariables(lumens, wattage, voltage);
	}

	// Secondary constructor
	public Light(String name, String description, String category, float price, String lumens, String wattage,
			String voltage) {
		// Initialize other fields to default values or null as needed
		super(null, name, description, category, price, null, Collections.emptyList(), null, null, null);
		populateLightVariables(lumens, wattage, voltage);
	}

	// Copy constructor
	public Light(Light other) {
		super(other.getId(), other.getName(), other.getDescription(), other.getCategory(), other.getPrice(),
				other.getPricingPeriod(), other.getMaintenanceLogs(), other.getRentalStatus(), other.getType(),
				other.getNextAvailableDate());
		populateLightVariables(other.lumens, other.wattage, other.voltage);
	}

	private void populateLightVariables(String lumens, String wattage, String voltage) {
		this.lumens = lumens;
		this.wattage = wattage;
		this.voltage = voltage;
	}

}
