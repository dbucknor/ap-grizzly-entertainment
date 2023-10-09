package com.grizzly.application.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 4125965356358329463L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private String customerId;
    private String firstname;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String password;
    private String imageUrl;

    // Primary constructor
    public Customer(String customerId, String firstname, String lastName,
                    String address, String phoneNumber, String password, String imageUrl) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    // Secondary constructor
    public Customer(String customerId, String firstname, String lastName) {
        this(customerId, firstname, lastName, null, null, null, null);
    }

    // Copy constructor
    public Customer(Customer other) {
        this(other.customerId, other.firstname, other.lastName,
             other.address, other.phoneNumber, other.password, other.imageUrl);
    }

    // Getters and setters for all fields

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // toString() method
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
