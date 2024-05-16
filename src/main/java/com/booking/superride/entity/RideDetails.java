package com.booking.superride.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
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

}
