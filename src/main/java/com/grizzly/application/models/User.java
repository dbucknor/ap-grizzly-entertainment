package com.grizzly.application.models;

import com.grizzly.application.models.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "User")
@Table(name = "User")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Transient
    private boolean loggedIn;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    public User() {
        this.id = null;
        this.password = null;
        this.email = null;
        this.loggedIn = false;
        this.type = null;
    }

    public User(String id, String email, String password, boolean loggedIn, UserType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.loggedIn = loggedIn;
        this.type = type;
    }

    public User(User user) {
        this.id = user.id;
        this.password = user.password;
        this.email = user.email;
        this.loggedIn = user.loggedIn;
        this.type = user.type;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", loggedIn=" + loggedIn +
                ", type=" + type +
                '}';
    }
}
