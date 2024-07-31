package com.booking.superride.controller;

import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.service.CustomerService;
import com.booking.superride.validation.CustomerDetailsValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
class CustomerController {

    private final CustomerService customerService;
    private final CustomerDetailsValidator validator;

    CustomerController(CustomerService customerService, CustomerDetailsValidator validator) {
        this.customerService = customerService;
        this.validator = validator;
    }

    @PostMapping("/register")
    ResponseEntity<CustomerDetails> saveCustomerDetails(@RequestBody @Valid CustomerDetailsDTO customerDetailsDTO) {
        validator.validateCustomerDetails(customerDetailsDTO);
        return ResponseEntity.ok(customerService.saveCustomerDetails(customerDetailsDTO));
    }
}
