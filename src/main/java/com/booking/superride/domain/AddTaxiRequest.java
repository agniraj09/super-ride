package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;

public record AddTaxiRequest(
        @NotBlank(message = "make cannot be blank") String make,
        @NotBlank(message = "taxiNumber cannot be blank") String taxiNumber,
        @NotBlank(message = "driverName cannot be blank") String driverName,
        @NotBlank(message = "currentLocation cannot be blank") char currentLocation) {
}
