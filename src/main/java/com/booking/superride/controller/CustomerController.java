package com.booking.superride.controller;

import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    ResponseEntity<CustomerDetails> saveCustomerDetails(@RequestBody @Valid CustomerDetailsDTO customerDetailsDTO) {
        return ResponseEntity.ok(customerService.saveCustomerDetails(customerDetailsDTO));
    }
}
