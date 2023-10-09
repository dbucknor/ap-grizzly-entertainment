package com.grizzly.application.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="Employee")
public class Employee implements Serializable{

    private static final long serialVersionUID = 4125965356358329462L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private String staffId;
    private String firstname;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String imageUrl;
    private String password;

    // Primary constructor
    public Employee(String staffId, String firstname, String lastName,
                    String address, String phoneNumber, String imageUrl, String password) {
        this.staffId = staffId;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.password = password;
    }

    // Secondary constructor
    public Employee(String staffId, String firstname, String lastName) {
        this(staffId, firstname, lastName, null, null, null, null);
    }

    // Copy constructor
    public Employee(Employee other) {
        this(other.staffId, other.firstname, other.lastName,
             other.address, other.phoneNumber, other.imageUrl, other.password);
    }

}
