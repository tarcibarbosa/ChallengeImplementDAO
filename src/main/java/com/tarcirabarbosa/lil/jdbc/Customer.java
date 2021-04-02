package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;

public class Customer implements DataTransferObject {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer(Customer customer) {
        this.id = customer.id;
        this.firstName = customer.firstName;
        this.lastName = customer.lastName;
        this.email = customer.email;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
