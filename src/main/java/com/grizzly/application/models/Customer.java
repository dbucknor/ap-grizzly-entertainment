package com.grizzly.application.models;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Customer implements Serializable {
	private static Object customer;
	public String customerID;
	public String firstName;
	public String lastName;
	public String address;
	public String phoneNumber;
	public String password;
	
	//Default Constructor
	public Customer() {
		this.customerID="";
		this.firstName="";
		this.lastName="";
		this.address="";
		this.phoneNumber="";
		this.password="";
	}
	
	public Customer(String customerID, String firstName, String lastName, String address, String phoneNumber,String password) {
		this.customerID=customerID;
		this.firstName=firstName;
		this.lastName=lastName;
		this.address=address;
		this.phoneNumber=phoneNumber;
		this.password=password;
	}
	
	public Customer(String customerID, String firstName,String lastName) {
		this(customerID,firstName,lastName,"","","");
	}
	
	public Customer(Customer customer) {
		this.customerID=customer.customerID;
		this.firstName=customer.firstName;
		this.lastName=customer.lastName;
		this.address=customer.address;
		this.phoneNumber=customer.phoneNumber;
		this.password=customer.password;
	}
	//Setters and Getters
	public String getCustomerID() {
		return customerID;
	}
	public void getCustomerID(String customerID) {
		this.customerID=customerID;
	}
	public String getFirstName(){
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber=phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String toString() {
		return("CustomerID: " + customerID + "First Name: " + firstName + "Last Name: " + lastName + "Address: " + address + "Phone Number: " + phoneNumber);	
	}
	public static void main(String[]args) {
		try (FileOutputStream fileOut = new FileOutputStream("Customer.ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(customer);
	        System.out.println("Customer object has been serialized to Customer.ser");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
