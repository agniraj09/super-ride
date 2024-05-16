package com.booking.superride.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_details")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_details_customerid_seq")
    @SequenceGenerator(name = "customer_details_customerid_seq", sequenceName = "customer_details_customerid_seq", allocationSize = 1)
    @Column(name = "customerid")
    private Long customerId;
    @Column(name = "customername")
    private String customerName;

}
