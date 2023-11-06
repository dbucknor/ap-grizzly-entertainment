package com.grizzly.application.models;

import com.grizzly.application.models.enums.UserType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
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
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
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
