package com.booking.superride.controller;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.service.TaxiService;
import com.booking.superride.validation.TaxiDetailsValidator;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxi")
class TaxiController {

    private final TaxiService taxiService;
    private final TaxiDetailsValidator validator;

    TaxiController(TaxiService taxiService, TaxiDetailsValidator validator) {
        this.taxiService = taxiService;
        this.validator = validator;
    }

    @PostMapping("/register")
    ResponseEntity<TaxiDetailsDTO> saveTaxiDetails(@RequestBody @Valid AddTaxiRequest addTaxiRequest) {
        validator.validateTaxiDetails(List.of(addTaxiRequest));
        return ResponseEntity.ok(taxiService.saveTaxiDetails(addTaxiRequest));
    }

    @PostMapping("/bulk/register")
    ResponseEntity<List<TaxiDetailsDTO>> saveTaxiDetailsBulk(@RequestBody List<AddTaxiRequest> addTaxiRequest) {
        validator.validateTaxiDetails(addTaxiRequest);
        return ResponseEntity.ok(taxiService.saveTaxis(addTaxiRequest));
    }
}
