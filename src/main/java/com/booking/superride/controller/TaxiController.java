package com.booking.superride.controller;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxi")
@RequiredArgsConstructor
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping("/add-taxi")
    public TaxiDetailsDTO saveTaxiDetails(@RequestBody @Valid AddTaxiRequest addTaxiRequest){
        return taxiService.saveTaxiDetails(addTaxiRequest);
    }
}
