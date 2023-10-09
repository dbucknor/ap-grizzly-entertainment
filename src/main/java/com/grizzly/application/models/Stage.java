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
@Table(name = "Stage")
public class Stage extends Equipment implements Serializable {

	private static final long serialVersionUID = 1L;

	private float width;
	private float height;
	private float length;

	// Primary constructor
	public Stage(String id, String name, String description, String category, float price, PricingPeriod pricingPeriod,
			List<MaintenanceLog> maintenanceLogs, RentalStatus rentalStatus, String type, Date nextAvailableDate,
			float width, float height, float length) {
		super(id, name, description, category, price, pricingPeriod, maintenanceLogs, rentalStatus, type,
				nextAvailableDate);
		populateStageFields(width, height, length);
	}

	// Secondary constructor
	public Stage(String name, String description, String category, float price, float width, float height,
			float length) {
		// Initialize other fields to default values or null as needed
		super(null, name, description, category, price, null, Collections.emptyList(), null, null, null);
		populateStageFields(width, height, length);
	}

	// Copy constructor
	public Stage(Stage other) {
		super(other.getId(), other.getName(), other.getDescription(), other.getCategory(), other.getPrice(),
				other.getPricingPeriod(), other.getMaintenanceLogs(), other.getRentalStatus(), other.getType(),
				other.getNextAvailableDate());
		populateStageFields(other.width, other.height, other.length);
	}

	private void populateStageFields(float width, float height, float length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}
}
