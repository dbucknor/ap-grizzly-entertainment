package project.grizzly.application.models;

import project.grizzly.application.models.enums.FormFieldType;
import project.grizzly.application.models.interfaces.ITableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "RentalRequest")
@Table(name = "rentalrequest")
public class RentalRequest implements Serializable, ITableEntity {
    @Id
    @Column(name = "requestId")
    private Integer requestId;
    @Column(name = "requestDate")
    private LocalDateTime requestDate;
    @Column(name = "approvedDate")
    private LocalDateTime approvedDate;
    @Column(name = "approved")
    private Boolean approved;
    @OneToOne
    @JoinColumn(name = "invoiceId")
    private Invoice invoice;
    @OneToOne(mappedBy = "request")
    private Transaction transaction;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approvedBy")
    private Employee approvedBy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer requestFrom;

    public RentalRequest() {
        this.requestId = null;
        this.requestDate = null;
        this.approved = false;
        this.invoice = null;
        this.approvedDate = null;
        this.transaction = null;
    }

    public RentalRequest(Integer requestId, LocalDateTime requestDate, Boolean approved, Invoice invoice, LocalDateTime approvedDate) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.approved = approved;
        this.invoice = invoice;
        this.approvedDate = approvedDate;
    }

    public RentalRequest(RentalRequest request) {
        this.approvedBy = request.approvedBy;
        this.requestId = request.requestId;
        this.requestFrom = request.requestFrom;
        this.requestDate = request.requestDate;
        this.approved = request.approved;
        this.invoice = request.invoice;
        this.approvedDate = request.approvedDate;
        this.transaction = request.transaction;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestID) {
        this.requestId = requestID;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime date) {
        this.requestDate = date;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Invoice getQuote() {
        return invoice;
    }

    public void setQuote(Invoice invoice) {
        this.invoice = invoice;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Boolean getApproved() {
        return approved;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee employee) {
        this.approvedBy = employee;
    }

    public Customer getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(Customer requestFrom) {
        this.requestFrom = requestFrom;
    }


    @Override
    public Object[] getValues() {
        return new Object[]{requestId, requestFrom.getCustomerId(), requestDate, approved, approvedBy != null ? approvedBy.getStaffId() : "", approvedDate};
    }

    @Override
    public String[] getTableTitles() {
        return new String[]{"Request Id", "Requested By", "Date", "Approved", "Approved By", "Approved Date"};
    }


    public List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig rid = new FieldConfig(Integer.class, "setRequestId", "getRequestId", "Request Id:", FormFieldType.NUMBER, true);
        rid.addConstraint(new Constraint(Constraint.NOT_NULL, "Request must be Invoice/Quote Id and not empty!"));
        fcs.add(rid);

        FieldConfig cid = new FieldConfig(String.class, "", "", "Customer Id:", FormFieldType.TEXT);
        fcs.add(cid);

        FieldConfig rDate = new FieldConfig(LocalDateTime.class, "setRequestDate", "getRequestDate", "Date:", FormFieldType.DATE);
        fcs.add(rDate);

        FieldConfig ap = new FieldConfig(Boolean.class, "setApproved", "getApproved", "Approved:", FormFieldType.TOGGLE);
        fcs.add(ap);

        FieldConfig apBy = new FieldConfig(String.class, "", "", "Approved By:", FormFieldType.TEXT);
        fcs.add(apBy);

        FieldConfig aDate = new FieldConfig(LocalDateTime.class, "setApprovedDate", "getApprovedDate", "Approved Date:", FormFieldType.DATE, LocalDateTime.now(), LocalDateTime.now().minusYears(10));
        fcs.add(aDate);

        return fcs;
    }


    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }
}
