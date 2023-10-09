package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "InvoiceDetails")
public class InvoiceDetails implements Serializable {

	private static final long serialVersionUID = 4125965356358329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int invoiceDetailId;

	@ManyToOne
	@JoinColumn(name = "invoiceId")
	private Invoices invoice;

	@ManyToOne
	@JoinColumn(name = "equipmentId")
	private transient Equipment equipment;

	private int quantity;
	private Date rentalStartDate;
	private Date rentalEndDate;

    // Primary constructor
    public InvoiceDetails(int invoiceDetailId, Invoices invoice, Equipment equipment,
                          int quantity, Date rentalStartDate, Date rentalEndDate) {
        this.invoiceDetailId = invoiceDetailId;
        this.invoice = invoice;
        this.equipment = equipment;
        this.quantity = quantity;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    // Secondary constructor
    public InvoiceDetails(Invoices invoice, Equipment equipment, int quantity,
                          Date rentalStartDate, Date rentalEndDate) {
        this(0, invoice, equipment, quantity, rentalStartDate, rentalEndDate);
    }

    // Copy constructor
    public InvoiceDetails(InvoiceDetails other) {
        this(other.invoiceDetailId, other.invoice, other.equipment,
             other.quantity, other.rentalStartDate, other.rentalEndDate);
    }

    // Getters and setters
    public int getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(int invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Invoices getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoices invoice) {
        this.invoice = invoice;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(Date rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public Date getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    // toString() method
    @Override
    public String toString() {
        return "InvoiceDetails{" +
                "invoiceDetailId=" + invoiceDetailId +
                ", invoice=" + invoice +
                ", equipment=" + equipment +
                ", quantity=" + quantity +
                ", rentalStartDate=" + rentalStartDate +
                ", rentalEndDate=" + rentalEndDate +
                '}';
    }
}
