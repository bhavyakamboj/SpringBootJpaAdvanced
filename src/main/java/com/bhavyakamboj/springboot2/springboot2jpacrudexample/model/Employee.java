package com.bhavyakamboj.springboot2.springboot2jpacrudexample.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="employees")
public class Employee {

    private long id;

    @NotNull
    @Size(min = 6, message="First name should have at least 6 characters")
    private String firstName;

    @NotNull
    @Size(min = 6, message = "Last name should have at least 6 characters")
    private String lastName;

    @Email
    @NotNull
    private String emailID;

    public Employee(){

    }

    public Employee(String firstName, String lastName, String emailID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="email_address", nullable = false)
    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailID='" + emailID + '\'' +
                '}';
    }
}
