package com.grizzly.application.models;

import com.grizzly.application.models.equipment.Equipment;
import com.grizzly.application.models.equipment.RentPeriod;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "InvoiceItem")
public class InvoiceItem<T extends Equipment> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    @Id
    private String invoiceId;
    @Transient
    private RentPeriod rentPeriod;
    @OneToOne
    private T equipment;
    @Basic
    private int quantity;
    @Basic
    private float totalPrice;

    public InvoiceItem() {
        this.itemId = 0;
        this.rentPeriod = null;
        this.equipment = null;
        this.quantity = 1;
        this.totalPrice = 0.0f;
    }

    public InvoiceItem(int itemId, String invoiceId, RentPeriod rentPeriod, T equipment, int quantity, float totalPrice) {
        this.itemId = itemId;
        this.invoiceId = invoiceId;
        this.rentPeriod = rentPeriod;
        this.equipment = equipment;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public InvoiceItem(InvoiceItem<T> invoiceItem) {
        this.itemId = invoiceItem.itemId;
        this.invoiceId = invoiceItem.invoiceId;
        this.rentPeriod = invoiceItem.rentPeriod;
        this.equipment = invoiceItem.equipment;
        this.quantity = invoiceItem.quantity;
        this.totalPrice = invoiceItem.totalPrice;
    }


    private void calculatePrice() {
        totalPrice = (rentPeriod.periodAs(equipment.getRentedPer()) * equipment.getPrice()) * quantity;
    }

    public T getEquipment() {
        return equipment;
    }

    public void setEquipment(T equipment) {
        this.equipment = equipment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
