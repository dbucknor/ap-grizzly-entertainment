package com.grizzly.application.models;

import com.grizzly.application.models.enums.FormFieldType;
import com.grizzly.application.models.equipment.Equipment;
import com.grizzly.application.models.equipment.RentPeriod;
import com.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "InvoiceItem")
public class InvoiceItem implements ITableEntity {
    @Id
    @Column(name = "invoiceItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceItemId;
    @Transient
    private RentPeriod rentPeriod;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;
    private Integer quantity;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;

    public InvoiceItem() {
        this.invoiceItemId = 0;
        this.rentPeriod = null;
        this.equipment = null;
        this.quantity = 1;
        this.totalPrice = 0.0;
    }

    public InvoiceItem(int invoiceItemId, String invoiceId, RentPeriod rentPeriod, Equipment equipment, int quantity, Double totalPrice) {
        this.invoiceItemId = invoiceItemId;
//        this.invoiceId = invoiceId;
        this.rentPeriod = rentPeriod;
        this.equipment = equipment;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public InvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItemId = invoiceItem.invoiceItemId;
//        this.invoiceId = invoiceItem.invoiceId;
        this.rentPeriod = invoiceItem.rentPeriod;
        this.equipment = invoiceItem.equipment;
        this.quantity = invoiceItem.quantity;
        this.totalPrice = invoiceItem.totalPrice;
    }

    private void calculatePrice() {
        totalPrice = (rentPeriod.periodAs(equipment.getRentedPer()) * equipment.getPrice()) * quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
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

    public void setQuantity(Number quantity) {
        this.quantity = quantity.intValue();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice.doubleValue();
    }

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer itemId) {
        this.invoiceItemId = itemId;
    }


//    public String getInvoiceId() {
//        return invoiceId;
//    }
//
//    public void setInvoiceId(String invoiceId) {
//        this.invoiceId = invoiceId;
//    }

    public RentPeriod getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(RentPeriod rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    public void setRentPeriodStart(LocalDateTime date) {
        if (this.rentPeriod == null) {
            this.rentPeriod = new RentPeriod();
        }
        try {
            this.rentPeriod.setStart(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setRentPeriodEnd(LocalDateTime date) {
        if (this.rentPeriod == null) {
            this.rentPeriod = new RentPeriod();
        }
        try {
            this.rentPeriod.setEnd(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setRentPeriodStart(String date) {

    }

    public void setRentPeriodEnd(String date) {

    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "itemId=" + invoiceItemId +
//                ", invoiceId='" + invoiceId + '\'' +
                ", rentPeriod=" + rentPeriod +
                ", equipment=" + equipment +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public Object[] getValues() {
        return new Object[]{invoice.getInvoiceId(), invoiceItemId, equipment.getEquipmentId(), quantity, rentPeriod != null ? rentPeriod.getStart() : "", rentPeriod != null ? rentPeriod.getEnd() : "", totalPrice};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Item Id", "Invoice Id", "Equipment Id", "Quantity", "Rent Period Start", "Rent Period End", "Total Price"};
    }

    public List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig tid = new FieldConfig(Integer.class, "setInvoiceItemId", "getInvoiceItemId", "Invoice Item Id:", FormFieldType.NUMBER, true);
        fcs.add(tid);

        FieldConfig inId = new FieldConfig(Integer.class, "", "", "Invoice Id", FormFieldType.NUMBER);
        fcs.add(inId);

        FieldConfig eid = new FieldConfig(String.class, "", "", "Equipment Id:", FormFieldType.TEXT);
        fcs.add(eid);

        FieldConfig qty = new FieldConfig(Integer.class, "setQuantity", "getQuantity", "Quantity:", FormFieldType.NUMBER, null, 1);
        fcs.add(qty);

        FieldConfig rps = new FieldConfig(LocalDateTime.class, "setRentPeriodStart", "getRentPeriodStart", "Rent Period Start:", FormFieldType.DATE);
        fcs.add(rps);

        FieldConfig rpe = new FieldConfig(LocalDateTime.class, "setRentPeriodEnd", "getRentPeriodEnd", "Rent Period End:", FormFieldType.DATE);
        fcs.add(rpe);

        FieldConfig tp = new FieldConfig(Double.class, "setTotalPrice", "getTotalPrice", "Total Price:", FormFieldType.NUMBER, null, 0.0);
        fcs.add(tp);

        return fcs;
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }
}
