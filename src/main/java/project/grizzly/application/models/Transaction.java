package project.grizzly.application.models;

import project.grizzly.application.models.enums.FormFieldType;
import project.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Transaction")
@Table(name = "Transaction")
public class Transaction implements Serializable, ITableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private int transactionId;
    private Double paid;
    @OneToOne
    @JoinColumn(name = "requestId")
    private RentalRequest request;
    private Double balance;
    @Column(name = "transactionDate")
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId")
    private Customer owner;

    public Transaction() {
        this.transactionId = 0;
        this.balance = 0.0;
        this.paid = 0.0;
        this.request = null;
        transactionDate = null;
    }

    public Transaction(Integer transactionId, Double paid, RentalRequest request, Double balance, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.paid = paid;
        this.request = request;
        this.balance = balance;
        this.transactionDate = transactionDate;
    }

    public Transaction(Transaction transaction) {
        this.transactionId = transaction.transactionId;
        this.paid = transaction.paid;
        this.request = transaction.request;
        this.balance = transaction.balance;
        this.transactionDate = transaction.transactionDate;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public RentalRequest getRequest() {
        return request;
    }

    public void setRequest(RentalRequest request) {
        this.request = request;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
        setBalance(getTotalPrice() - paid);
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
        setPaid(getTotalPrice() - balance);
    }

    public void calculateBalance() {
        balance = request.getInvoice().getTotalPrice() - paid;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setInvoiceId(Integer id) {
        this.getRequest().getInvoice().setInvoiceId(id);
    }

    public void setRequestId(Integer id) {
        this.getRequest().setRequestId(id);
    }

    public void setCustomerId(String id) {
        this.getRequest().getRequestFrom().setCustomerId(id);
        this.getRequest().getInvoice().getCustomer().setCustomerId(id);
    }

    public void setTotalPrice(Double price) {
        this.getRequest().getInvoice().setTotalPrice(price);
    }


    public Integer getInvoiceId(Integer id) {
        return this.getRequest().getInvoice().getInvoiceId();
    }

    public Integer getRequestId(Integer id) {
        return this.getRequest().getRequestId();
    }

    public String getCustomerId(String id) {
        return this.getRequest().getRequestFrom().getCustomerId();
    }

    public Double getTotalPrice() {
        return this.getRequest().getInvoice().getTotalPrice();
    }


    @Override
    public Object[] getValues() {
        return new Object[]{transactionId, request.getRequestId(), request.getInvoice().getInvoiceId(), request.getRequestFrom().getCustomerId(), request.getInvoice().getTotalPrice(), paid, balance, transactionDate};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Transaction Id", "Request Id", "Invoice Id", "Customer Id", "Total Price", "Total Paid", "Balance", "Transaction Date"};
    }

    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    private List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig tid = new FieldConfig(Integer.class, "setTransactionId", "getTransactionId", "Transaction Id: ", FormFieldType.NUMBER, true);
        tid.setDisabled(true);
        tid.addConstraint(new Constraint(Constraint.IS_NULL, "Transaction Id is automatically generated!"));
        fcs.add(tid);

        FieldConfig rid = new FieldConfig(Integer.class, "setRequestId", "getRequestId", "Request Id: ", FormFieldType.NUMBER);
        fcs.add(rid);

        FieldConfig iid = new FieldConfig(Integer.class, "setInvoiceId", "getInvoiceId", "Invoice Id: ", FormFieldType.NUMBER);
        fcs.add(iid);

        FieldConfig cid = new FieldConfig(String.class, "setCustomerId", "getCustomerId", "Customer Id: ", FormFieldType.TEXT);
        fcs.add(cid);

        FieldConfig tp = new FieldConfig(Double.class, "setTotalPrice", "getTotalPrice", "Total Price: $", FormFieldType.NUMBER);
        tp.setAllowsNegative(false);
        fcs.add(tp);

        FieldConfig pd = new FieldConfig(Double.class, "setPaid", "getPaid", "Paid: $", FormFieldType.NUMBER);
        tp.setAllowsNegative(false);
        fcs.add(pd);

        FieldConfig bl = new FieldConfig(Double.class, "setBalance", "getBalance", "Balance: $", FormFieldType.NUMBER);
        fcs.add(bl);

        FieldConfig tDate = new FieldConfig(LocalDateTime.class, "setTransactionDate", "getTransactionDate", "Transaction Date: ", FormFieldType.DATE, null, request != null ? request.getApprovedDate() : null);
        fcs.add(tDate);
        return fcs;
    }
}
