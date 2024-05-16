package com.booking.superride.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "taxi_details")
public class TaxiDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxi_details_taxiid_seq")
    @SequenceGenerator(name = "taxi_details_taxiid_seq", sequenceName = "taxi_details_taxiid_seq", allocationSize = 1)
    @Column(name = "taxiid")
    private Long taxiId;
    @Column(name = "make")
    private String make;
    @Column(name = "taxinumber")
    private String taxiNumber;
    @Column(name = "currentlocation")
    private char currentLocation;
    @Column(name = "drivername")
    private String driverName;
    private String status;

}
