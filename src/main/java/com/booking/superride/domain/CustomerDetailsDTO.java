package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerDetailsDTO(
        @NotBlank(message = "customerName cannot be blank") String customerName,
        @NotBlank(message = "mobileNumber cannot be blank")
                @Size(min = 10, max = 10, message = "mobileNumber should have 10 digits")
                Long mobileNumber) {}
