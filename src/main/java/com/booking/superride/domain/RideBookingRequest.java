package com.booking.superride.domain;

import java.time.LocalDateTime;

public record RideBookingRequest(
        Long customerId, char pickupPoint, char dropPoint, LocalDateTime pickupTime) {}
