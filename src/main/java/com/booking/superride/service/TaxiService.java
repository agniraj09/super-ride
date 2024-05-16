package com.booking.superride.service;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.mapper.TaxiMapper;
import com.booking.superride.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {

    private final TaxiMapper taxiMapper;
    private final TaxiRepository taxiRepository;

    public TaxiDetailsDTO saveTaxiDetails(AddTaxiRequest addTaxiRequest){
        var taxiDetails = taxiMapper.requestToDetails(addTaxiRequest);
        taxiDetails.setCurrentLocation('A'); // Default pick up location is 'A'
        taxiDetails.setStatus("Active"); // Default status - Active
        taxiDetails = taxiRepository.save(taxiDetails);
        return taxiMapper.detailsToDTO(taxiDetails);
    }
}
