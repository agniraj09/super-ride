package com.booking.superride.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "taxi_details")
public class TaxiDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    public TaxiDetails setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
        return this;
    }

    public TaxiDetails setMake(String make) {
        this.make = make;
        return this;
    }

    public TaxiDetails setTaxiNumber(String taxiNumber) {
        this.taxiNumber = taxiNumber;
        return this;
    }

    public TaxiDetails setCurrentLocation(char currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public TaxiDetails setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public TaxiDetails setStatus(String status) {
        this.status = status;
        return this;
    }
}
