package project.grizzly.application.models;

import project.grizzly.application.models.enums.FormFieldType;
import project.grizzly.application.models.enums.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "Customer")
@PrimaryKeyJoinColumn(name = "userId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends User {
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String customerId;
    private String address;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Invoice> invoices;
    @OneToMany(mappedBy = "requestFrom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RentalRequest> sentRequests;


    //Default Constructor
    public Customer() {
        this.customerId = "";
        this.address = "";
        this.phoneNumber = "";
        invoices = new HashSet<>();
    }

    public Customer(String userId, String customerId, String email, String password, String firstName, String lastName, boolean loggedIn, UserType accountType, String address, String phoneNumber) {
        super(userId, email, password, firstName, lastName, loggedIn, accountType);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.customerId = customerId;
        this.invoices = new HashSet<>();
        this.messages = new HashSet<>();
        this.sentRequests = new HashSet<>();
    }

    public Customer(Customer customer) {
        super(customer);
        this.customerId = customer.customerId;
        this.address = customer.address;
        this.phoneNumber = customer.phoneNumber;
        this.invoices = customer.invoices;
        this.sentRequests = customer.sentRequests;
    }

    //Setters and Getters
    public String getCustomerId() {
        return customerId;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<RentalRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(Set<RentalRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public Object[] getValues() {
        return new Object[]{userId, customerId, firstName, lastName, phoneNumber, address, email, password, accountType};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"User Id", "Customer Id", "First Name", "Last Name", "Phone Number", "Address", "Email", "Password", "Account Type"};
    }

    @Transient
    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    @Transient
    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>(super.configFields());

        FieldConfig id = new FieldConfig(String.class, "setCustomerId", "getCustomerId", "Customer Id", FormFieldType.TEXT, 50, 2, true);
        id.addConstraint(new Constraint(Constraint.IS_NULL, "Customer Id is auto generated!"));
        fcs.add(1, id);

        FieldConfig number = new FieldConfig(String.class, "setPhoneNumber", "getPhoneNumber", "Phone Number:", FormFieldType.TEXT, 10, 10);
        number.addConstraint(new Constraint(Constraint.NOT_NULL, "Phone Number must not be empty!"))
                .addConstraint(new Constraint(Constraint.MATCHES, "^[+]*[0-9]{0,3}[ ]{0,1}[(]{0,1}[0-9]{1,4}[)]{0,1}[ ]{0,1}[0-9]{1,3}[ ]{0,1}[-]{0,1}[ ]{0,1}[0-9]{4,4}", "Invalid Phone Number"));
        fcs.add(3, number);

        FieldConfig adrs = new FieldConfig(String.class, "setAddress", "getAddress", "Address:", FormFieldType.TEXT, 350, 5);
        adrs.addConstraint(new Constraint(Constraint.NOT_NULL, "Address must not be empty!"));
        fcs.add(4, adrs);

        return fcs;
    }

}
