package com.booking.superride.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RideDetailsDTO(
        Long rideId,
        Long taxiId,
        Long customerId,
        LocalDate rideDate,
        char pickupPoint,
        char dropPoint,
        int distance,
        double fare,
        LocalDateTime pickupTime,
        LocalDateTime dropTime) {}
