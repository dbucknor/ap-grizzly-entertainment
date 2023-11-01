package com.grizzly.application.models;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Employee implements Serializable {
	private static Object employee;
	public String staffID;
	public String firstName;
	public String lastName;
	public String phoneNumber;
	public String password;
	
	public Employee() {
		this.staffID="";
		this.firstName="";
		this.lastName="";
		this.phoneNumber="";
		this.password="";
	}
	public Employee(String staffID, String firstName, String lastName, String address, String phoneNumber,String password) {
		this.staffID=staffID;
		this.firstName=firstName;
		this.lastName=lastName;
		this.phoneNumber=phoneNumber;
		this.password=password;
	}
	public Employee(String staffID, String firstName,String lastName) {
		this(staffID,firstName,lastName,"","","");
	}
	public Employee(Employee emp) {
		this.staffID=emp.staffID;
		this.firstName=emp.firstName;
		this.lastName=emp.lastName;
		this.phoneNumber=emp.phoneNumber;
		this.password=emp.password;
	}
	public String getStaffID() {
		return staffID;
	}
	public void getStaffID(String staffID) {
		this.staffID=staffID;
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
		return("StaffID: " + staffID + "First Name: " + firstName + "Last Name: " + lastName + "Phone Number: " + phoneNumber);
	}
	public static void main(String[]args) {
		try (FileOutputStream fileOut = new FileOutputStream("Customer.ser");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(employee);
	        System.out.println("Customer object has been serialized to Customer.ser");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
