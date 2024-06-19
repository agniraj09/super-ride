package com.booking.superride.controller;

import com.booking.superride.domain.RideBookingRequest;
import com.booking.superride.domain.RideDetailsResponse;
import com.booking.superride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/book-ride")
    public ResponseEntity<RideDetailsResponse> bookRide(
            @RequestBody RideBookingRequest rideBookingRequest) {
        return ResponseEntity.ok(rideService.bookRide(rideBookingRequest));
    }
}
