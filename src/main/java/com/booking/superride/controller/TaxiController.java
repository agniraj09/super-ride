package com.booking.superride.controller;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/taxi")
@RequiredArgsConstructor
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping("/add-taxi")
    public ResponseEntity<TaxiDetailsDTO> saveTaxiDetails(@RequestBody @Valid AddTaxiRequest addTaxiRequest) {
        return ResponseEntity.ok(taxiService.saveTaxiDetails(addTaxiRequest));
    }

    @PostMapping("/bulk/add-taxi")
    public ResponseEntity<List<TaxiDetailsDTO>> saveTaxiDetailsBulk(@RequestBody List<AddTaxiRequest> addTaxiRequest) {
        return ResponseEntity.ok(taxiService.saveTaxis(addTaxiRequest));
    }
}
