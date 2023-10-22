package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Quote implements Serializable {
    private String quoteId;
    private String customerId;
    private String customerName;
    private Date date;
    private String deliveryAddress;
    private String equipmentTransport;
    private float totalPrice;
    private float discount;
    private ArrayList<Equipment> equipments;//asume there is an equipment list


    public Quote(String quoteId, String customerId, String customerName, Date date, String deliveryAddress,
                 String equipmentTransport, float totalPrice, float discount, ArrayList<Equipment> equipments) {
        this.quoteId = quoteId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.date = date;
        this.deliveryAddress = deliveryAddress;
        this.equipmentTransport = equipmentTransport;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.equipments = equipments;
    }


    public Quote(String quoteId, String customerId, String customerName, Date date) {
        this(quoteId, customerId, customerName, date, null, null, 0, 0, 0);
    }


    public Quote(Quote other) {
        this(other.quoteId, other.customerId, other.customerName, other.date, other.deliveryAddress,
                other.equipmentTransport, other.totalPrice, other.discount, other.equipments);
    }

    //toString
    @Override
    public String toString() {
        return "Quote{" +
                "quoteId='" + quoteId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", date=" + date +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", equipmentTransport='" + equipmentTransport + '\'' +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                ", equipments=" + equipments +
                '}';
    }


    public void generateInvoice() {
        System.out.println("Invoice for Quote ID: " + quoteId);
        System.out.println("Customer: " + customerName + " (ID: " + customerId + ")");
        System.out.println("Invoice Date: " + date);

        //if equipments is a list
        System.out.println("Equipment List:");
        for (Equipment equipment : equipments) {
            System.out.println("- " + equipment.getName() + " - $" + equipment.getPrice());
        }

        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Discount: $" + discount);

        float finalPrice = totalPrice - discount;
        System.out.println("Final Price: $" + finalPrice);
    }



    public String getQuoteId() {
        return quoteId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getDate() {
        return date;
    }



    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getEquipmentTransport() {
        return equipmentTransport;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    //if equiments is a list
    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }


    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setEquipmentTransport(String equipmentTransport) {
        this.equipmentTransport = equipmentTransport;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
