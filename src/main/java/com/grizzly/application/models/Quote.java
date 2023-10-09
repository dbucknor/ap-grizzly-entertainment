package com.grizzly.application.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "Quote")
public class Quote {

	private String quoteId;
	private String customerId;
	private String customerName;
	private Date date;
	private Timestamp time;
	private String deliveryAddress;
	private String equipmentTransport;
	private float totalPrice;
	private float discount;
	private List<Equipment> equipments;

	// Primary constructor
	public Quote(String quoteId, String customerId, String customerName, Date date, Timestamp time,
			String deliveryAddress, String equipmentTransport, float totalPrice, float discount,
			List<Equipment> equipments) {
		this.quoteId = quoteId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.date = date;
		this.time = time;
		this.deliveryAddress = deliveryAddress;
		this.equipmentTransport = equipmentTransport;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.equipments = equipments;
	}

	// Secondary constructor
	public Quote(String customerId, String customerName, Date date, Timestamp time, String deliveryAddress,
			String equipmentTransport, float totalPrice, float discount, List<Equipment> equipments) {
		this(null, customerId, customerName, date, time, deliveryAddress, equipmentTransport, totalPrice, discount,
				equipments);
	}

	// Copy constructor
	public Quote(Quote other) {
		this(other.quoteId, other.customerId, other.customerName, other.date, other.time, other.deliveryAddress,
				other.equipmentTransport, other.totalPrice, other.discount, other.equipments);
	}
}
