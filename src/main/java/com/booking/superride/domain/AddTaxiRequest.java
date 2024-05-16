package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddTaxiRequest {

    @NotBlank(message = "make cannot be blank")
    private String make;
    @NotBlank(message = "taxiNumber cannot be blank")
    private String taxiNumber;
    @NotBlank(message = "driverName cannot be blank")
    private String driverName;

}
