package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDetailsDTO {

    @NotBlank(message = "customerName cannot be blank")
    private String customerName;

}
