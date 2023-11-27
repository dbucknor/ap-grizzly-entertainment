package project.grizzly.application.models;

import project.grizzly.application.models.enums.FormFieldType;
import project.grizzly.application.models.equipment.Equipment;
import project.grizzly.application.models.equipment.RentPeriod;
import project.grizzly.application.models.interfaces.ITableEntity;
import jakarta.validation.constraints.NotNull;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipmentId")
    private Equipment equipment;
    private Integer quantity;
    private Boolean approved;
    @Embedded
    private RentPeriod rentPeriod;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;

    public InvoiceItem() {
        this.invoiceItemId = 0;
        this.equipment = null;
        this.quantity = 1;
        this.totalPrice = 0.0;
        this.approved = false;
        this.rentPeriod = new RentPeriod();
    }


    public InvoiceItem(int invoiceItemId, Boolean approved, Equipment equipment, int quantity, RentPeriod rentPeriod) {
        this.invoiceItemId = invoiceItemId;
        this.equipment = equipment;
        this.quantity = quantity;
        this.approved = approved;
        this.rentPeriod = rentPeriod;
        calculatePrice();
    }

    public InvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItemId = invoiceItem.invoiceItemId;
        this.invoice = invoiceItem.invoice;
        this.approved = invoiceItem.approved;
        this.equipment = invoiceItem.equipment;
        this.quantity = invoiceItem.quantity;
        this.totalPrice = invoiceItem.totalPrice;
        this.rentPeriod = invoiceItem.rentPeriod;
    }

    public void calculatePrice() {
        this.totalPrice = (rentPeriod.periodAs(equipment.getRentedPer()) * equipment.getPrice()) * quantity;
        System.out.println(this.totalPrice);
        System.out.println("rp: " + rentPeriod.periodAs(equipment.getRentedPer()) + "ep: " + equipment.getPrice() + "q: " + quantity);
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
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

    public RentPeriod getRentPeriod() {
        return rentPeriod;
    }


    public void setRentPeriod(RentPeriod rentPeriod) {
        this.rentPeriod = rentPeriod;
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

    public LocalDateTime getRentalStartDate() {
        return rentPeriod.getRentalStartDate();
    }

    @NotNull
    public void setRentalStartDate(LocalDateTime rentalStartDate) {
        try {
            rentPeriod.setRentalStartDate(rentalStartDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LocalDateTime getRentalEndDate() {
        return rentPeriod.getRentalEndDate();
    }

    @NotNull
    public void setRentalEndDate(LocalDateTime rentalEndDate) {
        try {
            rentPeriod.setRentalEndDate(rentalEndDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object[] getValues() {
        return new Object[]{invoiceItemId, invoice.getInvoiceId(), equipment.getEquipmentId(), quantity, totalPrice, rentPeriod.getRentalStartDate(), rentPeriod.getRentalEndDate(), approved};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Item Id", "Invoice Id", "Equipment Id", "Quantity", "Total Price", "Renat Start", "Rent End", "Approved"};
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

        FieldConfig rsd = new FieldConfig(LocalDateTime.class, "setRentalStartDate", "getRentalStartDate", "Rent Period Start:", FormFieldType.DATE);
        fcs.add(rsd);

        FieldConfig red = new FieldConfig(LocalDateTime.class, "setRentalEndDate", "getRentalEndDate", "Rent Period End:", FormFieldType.DATE);
        fcs.add(red);

        FieldConfig tp = new FieldConfig(Double.class, "setTotalPrice", "getTotalPrice", "Total Price:", FormFieldType.NUMBER, null, 0.0);
        fcs.add(tp);

        FieldConfig ap = new FieldConfig(Boolean.class, "setApproved", "getApproved", "Approved", FormFieldType.TOGGLE);
        fcs.add(ap);

        return fcs;
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceItemId=" + invoiceItemId +
                ", equipment=" + equipment +
                ", quantity=" + quantity +
                ", approved=" + approved +
                ", totalPrice=" + totalPrice +
                ", invoice=" + invoice +
                '}';
    }
}
