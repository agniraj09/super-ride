package com.booking.superride.entity.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OnTripTaxiDetails {

    Long getTaxiId();

    LocalDate getRideDate();

    char getPickupPoint();

    char getDropPoint();

    long getDistance();

    double getFare();

    LocalDateTime getPickupTime();

    LocalDateTime getDropTime();

}
