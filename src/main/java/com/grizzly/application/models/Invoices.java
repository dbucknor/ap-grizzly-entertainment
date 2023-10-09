package com.grizzly.application.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="Invoices")
public class Invoices implements Serializable{
	
    private static final long serialVersionUID = 4125965356358329464L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceId;
    private int customerId;
    private Date invoiceDate;
    private BigDecimal totalPrice;

    // Primary constructor
    public Invoices(int invoiceId, int customerId, Date invoiceDate, BigDecimal totalPrice) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
    }

    // Secondary constructor
    public Invoices(int customerId, Date invoiceDate, BigDecimal totalPrice) {
        this(0, customerId, invoiceDate, totalPrice); // Auto-generated invoiceId
    }

    // Copy constructor
    public Invoices(Invoices other) {
        this(other.invoiceId, other.customerId, other.invoiceDate, other.totalPrice);
    }

}
