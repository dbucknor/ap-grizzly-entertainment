package com.grizzly.application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Equipment implements Serializable{
	private String id;
	private String name;
	private String description;
	private String category;
	private float price;
	private enum pricingPeriod{
		
	};
	ArrayList<MaintainanceLog> maintainaceLogs = new ArrayList<MaintainaceLog>();
	private enum rentalStatus{
		
	};
	private String type;
	private Date nextAvaliableDate;
	
	 public Equipment() {
        // You can initialize default values for your fields here if needed
        id = "";
        name = "";
        description = "";
        category = "";
        price = 0.0f;
        type = "";
        nextAvaliableDate = null;
	   }
	
	public Equipment(String id, String name, String description, String category, float price,
			ArrayList<MaintainanceLog> maintainaceLogs, String type, Date nextAvaliableDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.maintainaceLogs = maintainaceLogs;
		this.type = type;
		this.nextAvaliableDate = nextAvaliableDate;
	}
	
	 public Equipment(Equipment other) {
	        this.id = other.id;
	        this.name = other.name;
	        this.description = other.description;
	        this.category = other.category;
	        this.price = other.price;
	        this.maintainaceLogs = new ArrayList<>(other.maintainaceLogs); // Copy the list
	        this.type = other.type;
	        this.nextAvaliableDate = other.nextAvaliableDate;
	    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public ArrayList<MaintainanceLog> getMaintainaceLogs() {
		return maintainaceLogs;
	}

	public void setMaintainaceLogs(ArrayList<MaintainanceLog> maintainaceLogs) {
		this.maintainaceLogs = maintainaceLogs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getNextAvaliableDate() {
		return nextAvaliableDate;
	}

	public void setNextAvaliableDate(Date nextAvaliableDate) {
		this.nextAvaliableDate = nextAvaliableDate;
	}

	public float getQuote(float quote) {
		
		return quote;
		
	}
	
	public void getLastMaintainanceLog() {
		
	}
	
	public void getCondition() {
		
	}
	
	public void calculateRentalCost() {
		
	}
	
	@Override
	public String toString() {
		return "Equipment [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", price=" + price + ", type=" + type + ", nextAvaliableDate=" + nextAvaliableDate + "]";
	}
	 
	
	
}
