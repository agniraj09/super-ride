package com.booking.superride.domain;

public record RideDetailsResponse(
        String status,
        String message,
        RideDetailsDTO rideDetails) {
}
