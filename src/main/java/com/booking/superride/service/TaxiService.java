package com.booking.superride.service;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.mapper.TaxiMapper;
import com.booking.superride.repository.TaxiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {

    private final TaxiMapper taxiMapper;
    private final TaxiRepository taxiRepository;

    @Transactional
    public TaxiDetailsDTO saveTaxiDetails(AddTaxiRequest addTaxiRequest){
        var taxiDetails = taxiMapper.requestToDetails(addTaxiRequest).setCurrentLocation('A').setStatus("Active");
        taxiDetails = taxiRepository.save(taxiDetails);
        return taxiMapper.detailsToDTO(taxiDetails);
    }
}
