package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.grizzly.application.enums.PricingPeriod;
import com.grizzly.application.enums.RentalStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@Entity
@Table(name = "Equipment")
public class Equipment implements Serializable {

	private static final long serialVersionUID = 4125965356358329461L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String name;
	private String description;
	private String category;
	private float price;
	private PricingPeriod pricingPeriod;
	private List<MaintenanceLog> maintenanceLogs;
	private RentalStatus rentalStatus;
	private String type;
	private Date nextAvailableDate;

	public Equipment() {}
	
	// Primary constructor
	public Equipment(String id, String name, String description, String category, float price,
			PricingPeriod pricingPeriod, List<MaintenanceLog> maintenanceLogs, RentalStatus rentalStatus, String type,
			Date nextAvailableDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.pricingPeriod = pricingPeriod;
		this.maintenanceLogs = maintenanceLogs;
		this.rentalStatus = rentalStatus;
		this.type = type;
		this.nextAvailableDate = nextAvailableDate;
	}

	// Secondary constructor
	public Equipment(String name, String description, String category, float price) {
		// Initialize other fields to default values or null as needed
		this(null, name, description, category, price, null, Collections.emptyList(), null, null, null);
	}

	// Copy constructor
	public Equipment(Equipment other) {
		this.id = other.id;
		this.name = other.name;
		this.description = other.description;
		this.category = other.category;
		this.price = other.price;
		this.pricingPeriod = other.pricingPeriod;
		this.rentalStatus = other.rentalStatus;
		this.type = other.type;
		this.nextAvailableDate = other.nextAvailableDate;
	}
	
	
	public Quote getQuote(float price) {
		return new Quote();
	}

	public MaintenanceLog getLastMaintenanceLog() {
		// Get recent maintenance log sorted by date
		maintenanceLogs.sort(Comparator.comparing(MaintenanceLog::getMaintenanceDate).reversed());
		return maintenanceLogs.get(0);
	}

	public String getCondition() {
		// TODO: To be discussed and implemented
		return null;
	}

	public float calculateRentalCost() {
		// TODO: To be discussed and implemented
		return Float.MIN_NORMAL;
	}
}
