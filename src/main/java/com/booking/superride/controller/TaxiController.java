package com.booking.superride.controller;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.service.TaxiService;
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

    public TaxiController(TaxiService taxiService) {
        this.taxiService = taxiService;
    }

    @PostMapping("/register")
    ResponseEntity<TaxiDetailsDTO> saveTaxiDetails(@RequestBody @Valid AddTaxiRequest addTaxiRequest) {
        return ResponseEntity.ok(taxiService.saveTaxiDetails(addTaxiRequest));
    }

    @PostMapping("/bulk/register")
    ResponseEntity<List<TaxiDetailsDTO>> saveTaxiDetailsBulk(@RequestBody List<AddTaxiRequest> addTaxiRequest) {
        return ResponseEntity.ok(taxiService.saveTaxis(addTaxiRequest));
    }
}
