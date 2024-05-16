package com.booking.superride.domain;

public record TaxiDetailsDTO(Long taxiId, String make, String taxiNumber, char currentLocation, String driverName, String status) {
}
