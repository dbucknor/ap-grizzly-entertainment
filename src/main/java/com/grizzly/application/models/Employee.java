package com.grizzly.application.models;

import com.grizzly.application.models.enums.FormFieldType;
import com.grizzly.application.models.enums.UserType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity(name = "Employee")
@Table(name = "Employee")
@PrimaryKeyJoinColumn(name = "userId")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {
    @Column(name = "staffId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String staffId;

    //    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RentalRequest> approvedRequests;

    public Employee() {
        this.staffId = "";
    }

    public Employee(String userId, String staffId, String email, String password, String firstName, String lastName, boolean loggedIn, UserType accountType) {
        super(userId, email, password, firstName, lastName, loggedIn, accountType);
        this.staffId = staffId;
        this.userId = userId;
    }

    public Employee(Employee emp) {
        super(emp);
        this.approvedRequests = emp.approvedRequests;
        this.staffId = emp.staffId;
        this.userId = emp.userId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffID) {
        this.staffId = staffID;
    }

    public Set<RentalRequest> getApprovedRequests() {
        return approvedRequests;
    }

    public void setApprovedRequests(Set<RentalRequest> approvedRequests) {
        this.approvedRequests = approvedRequests;
    }

    @Override
    public Object[] getValues() {
        return new Object[]{userId, staffId, firstName, lastName, email, password, accountType};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"User Id", "Staff Id", "First Name", "Last Name", "Email", "Password", "Account Type"};
    }

    @Transient
    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    @Transient
    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>(super.configFields());

        FieldConfig id = new FieldConfig(String.class, "setStaffId", "getStaffId", "Staff Id", FormFieldType.TEXT, 50, 2, true);
        id.addConstraint(new Constraint(Constraint.IS_NULL, "Staff Id is auto generated!"));
        fcs.add(1, id);

        return fcs;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "staffID='" + staffId + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", loggedIn=" + loggedIn +
                ", type=" + accountType +
                '}';
    }
}
