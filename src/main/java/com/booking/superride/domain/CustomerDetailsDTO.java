package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerDetailsDTO(
        @NotBlank(message = "customerName cannot be blank") String customerName,
        @Pattern(regexp = "^\\d{10}$", message = "mobileNumber should have 10 digits") String mobileNumber) {}
