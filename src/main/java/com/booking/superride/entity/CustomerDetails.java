package com.booking.superride.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_details")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customerid")
    private Long customerId;

    @Column(name = "customername")
    private String customerName;

    public CustomerDetails setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerDetails setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
}
