package com.grizzly.application.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grizzly.application.models.enums.FormFieldType;
import com.grizzly.application.models.enums.UserType;
import com.grizzly.application.models.interfaces.ITableEntity;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity(name = "User")
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable, ITableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    protected String userId;
    @Column(unique = true)
    protected String email;
    protected String password;
    @Column(name = "firstName")
    protected String firstName;
    @Column(name = "lastName")
    protected String lastName;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected Set<Message> messages;
    @Transient
    protected boolean loggedIn;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "accountType")
    protected UserType accountType;

    public User() {
        this.userId = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.loggedIn = false;
        this.accountType = UserType.CUSTOMER;
    }

    public User(String userId, String email, String password, String firstName, String lastName, boolean loggedIn, UserType accountType) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loggedIn = loggedIn;
        this.accountType = accountType;
    }

    public User(User user) {
        this.userId = user.userId;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.loggedIn = user.loggedIn;
        this.accountType = user.accountType;
        this.messages = user.getMessages();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public UserType getAccountType() {
        return accountType;
    }

    public void setAccountType(UserType type) {
        this.accountType = type;
    }

    public void setAccountType(String type) {
        this.accountType = UserType.valueOf(type);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    @Override
    public Object[] getValues() {
        return new Object[]{userId, firstName, lastName, email, password, accountType};
    }

    @Transient
    @Override
    public String[] getTableTitles() {
        return new String[]{"User Id", "First Name", "Last Name", "Email", "Password", "Account Type"};
    }

    @Transient
    @Override
    public TableConfig createEntityTableCfg() {
        return new TableConfig(getTableTitles(), configFields());
    }

    @Transient
    protected List<FieldConfig> configFields() {
        List<FieldConfig> fcs = new ArrayList<>();

        FieldConfig id = new FieldConfig(String.class, "setUserId", "getUserId", "User Id", FormFieldType.TEXT, 50, 2, true);
        id.addConstraint(new Constraint(Constraint.NOT_NULL, "User Id must not be empty!"));
        fcs.add(id);

        FieldConfig fName = new FieldConfig(String.class, "setFirstName", "getFirstName", "First Name:", FormFieldType.TEXT, 50, 2);
        fName.addConstraint(new Constraint(Constraint.NOT_NULL, "First Name must not be empty!"));
        fcs.add(fName);

        FieldConfig lName = new FieldConfig(String.class, "setLastName", "getLastName", "Last Name:", FormFieldType.TEXT, 50, 2);
        lName.addConstraint(new Constraint(Constraint.NOT_NULL, "Last Name must not be empty!"));
        fcs.add(lName);

        FieldConfig mail = new FieldConfig(String.class, "setEmail", "getEmail", "Email:", FormFieldType.TEXT, 50, 2);
        mail.addConstraint(new Constraint(Constraint.NOT_NULL, "Email must not be empty!"))
                .addConstraint(new Constraint(Constraint.MATCHES, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", "Invalid Email!"));
        fcs.add(mail);

        FieldConfig pswd = new FieldConfig(String.class, "setPassword", "getPassword", "Password:", FormFieldType.TEXT, 24, 8);
        pswd.addConstraint(new Constraint(Constraint.NOT_NULL, "Password must not be empty!"))
                .addConstraint(new Constraint(Constraint.MATCHES, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,24}$", "Password is too weak! Ensure to use a combination of numbers, symbols and uppercase and lowercase letters."));
        fcs.add(pswd);

        FieldConfig type = new FieldConfig(UserType.class, "setAccountType", "getAccountType", "Account Type:", FormFieldType.SELECT, UserType.values());
        fcs.add(type);

        return fcs;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", loggedIn=" + loggedIn +
                ", type=" + accountType +
                '}';
    }
}
