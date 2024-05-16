package com.booking.superride.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideBookingRequest {
    private Long customerId;
    private char pickupPoint;
    private char dropPoint;
    private LocalDateTime pickupTime;
}
