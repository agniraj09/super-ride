package com.booking.superride.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "ride_details")
public class RideDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_details_rideid_seq")
    @SequenceGenerator(name = "ride_details_rideid_seq", sequenceName = "ride_details_rideid_seq", allocationSize = 1)
    @Column(name = "rideid")
    private Long rideId;
    @Column(name = "taxiid")
    private Long taxiId;
    @Column(name = "customerid")
    private Long customerId;
    @Column(name = "ridedate")
    private LocalDate rideDate;
    @Column(name = "pickuppoint")
    private char pickupPoint;
    @Column(name = "droppoint")
    private char dropPoint;
    @Column(name = "distance")
    private int distance;
    @Column(name = "fare")
    private double fare;
    @Column(name = "pickuptime")
    private LocalDateTime pickupTime;
    @Column(name = "droptime")
    private LocalDateTime dropTime;

    public RideDetails setRideId(Long rideId) {
        this.rideId = rideId;
        return this;
    }

    public RideDetails setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
        return this;
    }

    public RideDetails setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public RideDetails setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
        return this;
    }

    public RideDetails setPickupPoint(char pickupPoint) {
        this.pickupPoint = pickupPoint;
        return this;
    }

    public RideDetails setDropPoint(char dropPoint) {
        this.dropPoint = dropPoint;
        return this;
    }

    public RideDetails setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public RideDetails setFare(double fare) {
        this.fare = fare;
        return this;
    }

    public RideDetails setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
        return this;
    }

    public RideDetails setDropTime(LocalDateTime dropTime) {
        this.dropTime = dropTime;
        return this;
    }
}
