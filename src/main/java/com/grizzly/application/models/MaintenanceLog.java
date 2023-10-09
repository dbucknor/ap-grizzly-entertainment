package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "Maintenance_Log")
public class MaintenanceLog implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private Date maintenanceDate;
	private String description;
	private String resolution;
	private float maintenanceCost;

	
	 // Primary constructor
    public MaintenanceLog(String id, Date maintenanceDate, String description, String resolution, float maintenanceCost) {
        this.id = id;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.resolution = resolution;
        this.maintenanceCost = maintenanceCost;
    }

    // Secondary constructor
    public MaintenanceLog(Date maintenanceDate, String description, String resolution, float maintenanceCost) {
        this(null, maintenanceDate, description, resolution, maintenanceCost);
    }

    // Copy constructor
    public MaintenanceLog(MaintenanceLog other) {
        this(other.id, other.maintenanceDate, other.description, other.resolution, other.maintenanceCost);
    }
}
