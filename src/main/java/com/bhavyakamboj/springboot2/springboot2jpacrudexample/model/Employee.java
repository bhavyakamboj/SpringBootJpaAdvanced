package com.bhavyakamboj.springboot2.springboot2jpacrudexample.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="employees")
@ApiModel(description = "All details about Employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee extends Auditable<String>{

    @ApiModelProperty(notes = "The database generated employee ID")
    private long id;

    @NotNull
    @Size(min = 6, message="First name should have at least 6 characters")
    @ApiModelProperty(notes = "Employee's first name")
    private String firstName;

    @NotNull
    @Size(min = 6, message = "Last name should have at least 6 characters")
    @ApiModelProperty(notes = "Employee's last name")
    private String lastName;

    @Email
    @NotNull
    @ApiModelProperty(notes = "Employee's email ID")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (!firstName.equals(employee.firstName)) return false;
        if (!lastName.equals(employee.lastName)) return false;
        return emailID.equals(employee.emailID);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + emailID.hashCode();
        return result;
    }
}
