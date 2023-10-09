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
@Table(name = "Power")
public class Power extends Equipment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String outputPower;
	private String phase;
	private String outputVoltage;
	private String fuelSource;

	public Power(String id, String name, String description, String category, float price, PricingPeriod pricingPeriod,
			List<MaintenanceLog> maintenanceLogs, RentalStatus rentalStatus, String type, Date nextAvailableDate,
			String outputPower, String phase, String outputVoltage, String fuelSource) {
		super(id, name, description, category, price, pricingPeriod, maintenanceLogs, rentalStatus, type,
				nextAvailableDate);
		populatePowerFields(outputPower, phase, outputVoltage, fuelSource);
	}

	// Secondary constructor
	public Power(String name, String description, String category, float price, String outputPower, String phase,
			String outputVoltage, String fuelSource) {
		// Initialize other fields to default values or null as needed
		super(null, name, description, category, price, null, Collections.emptyList(), null, null, null);
		populatePowerFields(outputPower, phase, outputVoltage, fuelSource);
	}

	// Copy constructor
	public Power(Power other) {
		super(other.getId(), other.getName(), other.getDescription(), other.getCategory(), other.getPrice(),
				other.getPricingPeriod(), other.getMaintenanceLogs(), other.getRentalStatus(), other.getType(),
				other.getNextAvailableDate());
		populatePowerFields(other.outputPower, other.phase, other.outputVoltage, other.fuelSource);
	}

	private void populatePowerFields(String outputPower, String phase, String outputVoltage, String fuelSource) {
		this.outputPower = outputPower;
		this.phase = phase;
		this.outputVoltage = outputVoltage;
		this.fuelSource = fuelSource;
	}
}
