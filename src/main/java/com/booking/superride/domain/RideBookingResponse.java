package com.booking.superride.domain;

import com.booking.superride.entity.RideDetails;
import lombok.Data;

@Data
public class RideBookingResponse {

    private String status;
    private String message;
    private RideDetails rideDetails;

}
