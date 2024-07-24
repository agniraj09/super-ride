package com.booking.superride.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ride_details")
public class RideDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    public Long getRideId() {
        return rideId;
    }

    public Long getTaxiId() {
        return taxiId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public char getPickupPoint() {
        return pickupPoint;
    }

    public char getDropPoint() {
        return dropPoint;
    }

    public int getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public LocalDateTime getDropTime() {
        return dropTime;
    }
}
