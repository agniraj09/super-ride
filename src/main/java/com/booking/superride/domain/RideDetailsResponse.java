package com.booking.superride.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideDetailsResponse {

    private String status;
    private String message;
    private RideDetailsDTO rideDetailsDTO;

}
