package com.grizzly.application.models;

import java.io.Serializable;
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
@Table(name = "Sound")
public class Sound extends Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	private String wattage;
	private String inputVoltage;
	private String peakDb;
	private String ampType;

	// Primary constructor
	public Sound(String id, String name, String description, String category, float price, PricingPeriod pricingPeriod,
			List<MaintenanceLog> maintenanceLogs, RentalStatus rentalStatus, String type, Date nextAvailableDate,
			String wattage, String inputVoltage, String peakDb, String ampType) {
		super(id, name, description, category, price, pricingPeriod, maintenanceLogs, rentalStatus, type,
				nextAvailableDate);
		populateSoundFields(wattage, inputVoltage, peakDb, ampType);
	}

	// Secondary constructor
	public Sound(String name, String description, String category, float price, String wattage, String inputVoltage,
			String peakDb, String ampType) {
		// Initialize other fields to default values or null as needed
		super(null, name, description, category, price, null, Collections.emptyList(), null, null, null);
		populateSoundFields(wattage, inputVoltage, peakDb, ampType);
	}

	// Copy constructor
	public Sound(Sound other) {
		super(other.getId(), other.getName(), other.getDescription(), other.getCategory(), other.getPrice(),
				other.getPricingPeriod(), other.getMaintenanceLogs(), other.getRentalStatus(), other.getType(),
				other.getNextAvailableDate());
		populateSoundFields(other.wattage, other.inputVoltage, other.peakDb, other.ampType);
	}

	private void populateSoundFields(String wattage, String inputVoltage, String peakDb, String ampType) {
		this.wattage = wattage;
		this.inputVoltage = inputVoltage;
		this.peakDb = peakDb;
		this.ampType = ampType;
	}

}