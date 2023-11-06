package com.grizzly.application.models;

import com.grizzly.application.models.enums.FormFieldType;
import com.grizzly.application.models.enums.TransportOption;
import com.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Invoice")
@Table(name = "Invoice")
public class Invoice implements ITableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoiceId")
    private String invoiceId;
    @Column(name = "invoiceDate")
    private LocalDateTime invoiceDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private User customer;
    @Column(name = "deliveryAddress")
    private String deliveryAddress;
    @Column(name = "deliveryOption")
    @Enumerated(EnumType.STRING)
    private TransportOption deliveryOption;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @Column(name = "discount")
    private Double discount;
    @OneToOne(mappedBy = "invoice")
    private RentalRequest rentalRequest;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<InvoiceItem> items;

    public Invoice() {
        this.invoiceId = null;
        this.invoiceDate = null;
        this.deliveryAddress = null;
        this.deliveryOption = null;
        this.totalPrice = 0.0;
        this.discount = 0.0;
        this.items = new HashSet<>();
        this.customer = null;
    }

    public Invoice(String invoiceId, LocalDateTime invoiceDate, String deliveryAddress,
                   TransportOption deliveryOption, Double totalPrice, Double discount, Set<InvoiceItem> items) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.deliveryAddress = deliveryAddress;
        this.deliveryOption = deliveryOption;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.items = items;
        this.customer = null;
    }


    public Invoice(Invoice other) {
        this(other.invoiceId, other.invoiceDate, other.deliveryAddress,
                other.deliveryOption, other.totalPrice, other.discount, other.items);
        this.customer = other.customer;
    }

    //toString
    @Override
    public String toString() {
        return "Quote{" +
                "quoteId='" + invoiceId + '\'' +
//                ", customerId='" + customerId + '\'' +
                ", date=" + invoiceDate +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", equipmentTransport='" + deliveryOption + '\'' +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                ", equipments=" + items +
                '}';
    }


    public void generateInvoice() {
        System.out.println("Invoice for Quote ID: " + invoiceId);
//        System.out.println("Customer: " + customerName + " (ID: " + customerId + ")");
        System.out.println("Invoice Date: " + invoiceDate);

        //if equipments is a list
//        System.out.println("Equipment List:");
//        for (InvoiceItem equipment : items) {
//            System.out.println("- " + equipment.getName() + " - $" + equipment.getPrice());
//        }

        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Discount: $" + discount);

        double finalPrice = totalPrice - discount;
        System.out.println("Final Price: $" + finalPrice);
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public RentalRequest getRentalRequest() {
        return rentalRequest;
    }

    public void setRentalRequest(RentalRequest rentalRequest) {
        this.rentalRequest = rentalRequest;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceId(String quoteId) {
        this.invoiceId = quoteId;
    }

    public void setInvoiceDate(LocalDateTime date) {
        this.invoiceDate = date;
    }

    public void setItems(Set<InvoiceItem> equipments) {
        this.items = equipments;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public TransportOption getDeliveryOption() {
        return deliveryOption;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setDeliveryOption(String option) {
        this.deliveryOption = TransportOption.valueOf(option);
    }

    public Double getDiscount() {
        return discount;
    }

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryOption(TransportOption equipmentTransport) {
        this.deliveryOption = equipmentTransport;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice.doubleValue();
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setDiscount(Number discount) {
        this.discount = discount.doubleValue();
    }

    @Override
    public Object[] getValues() {
        return new Object[]{invoiceId, customer.getFirstName() + " " + customer.getLastName(), deliveryAddress, deliveryOption, discount, totalPrice, invoiceDate};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Invoice Id", "Customer Name", "Delivery Address", "Delivery Option", "Discount", "Total Price", "Invoice Date"};
    }

    public List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig id = new FieldConfig(String.class, "setInvoiceId", "getInvoiceId", "Invoice Id", FormFieldType.TEXT, true);
        id.setDisabled(true);
        id.addConstraint(new Constraint(Constraint.IS_NULL, "Invoice id is automatically generated!"));
        fcs.add(id);

        //        FieldConfig number = new FieldConfig(String.class, "setCustomerId", "Customer Id:", FormFieldType.TEXT);
        FieldConfig dOption = new FieldConfig(TransportOption.class, "setDeliveryOption", "getDeliveryOption", "Delivery Transport:", FormFieldType.SELECT, TransportOption.values());
        fcs.add(dOption);

        FieldConfig dAddress = new FieldConfig(String.class, "setDeliveryAddress", "getDeliveryAddress", "Delivery Address:", FormFieldType.LONGTEXT, 350, 5);
        fcs.add(dAddress);

        FieldConfig disc = new FieldConfig(Double.class, "setDiscount", "getDiscount", "Discount:", FormFieldType.NUMBER, null, 0.0);
        disc.setAllowsNegative(false);
        fcs.add(disc);

        FieldConfig tPrice = new FieldConfig(Double.class, "setTotalPrice", "getTotalPrice", "Total Price:", FormFieldType.NUMBER, null, 0.0);
        tPrice.setAllowsNegative(false);
        fcs.add(tPrice);

        FieldConfig inDate = new FieldConfig(String.class, "setInvoiceDate", "getInvoiceDate", "Invoice Date:", FormFieldType.DATE, LocalDateTime.now().plusYears(5), LocalDateTime.now());
        fcs.add(inDate);

        return fcs;
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }
}
