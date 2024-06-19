package com.booking.superride.domain;

import jakarta.validation.constraints.NotBlank;

public record CustomerDetailsDTO(
        @NotBlank(message = "customerName cannot be blank") String customerName) {}
