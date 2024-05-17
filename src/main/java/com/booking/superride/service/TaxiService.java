package com.booking.superride.service;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.mapper.TaxiMapper;
import com.booking.superride.repository.TaxiRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiService {

    private final TaxiMapper taxiMapper;
    private final TaxiRepository taxiRepository;

    @Transactional
    public TaxiDetailsDTO saveTaxiDetails(AddTaxiRequest addTaxiRequest) {
        var taxiDetails = taxiMapper.requestToDetails(addTaxiRequest);
        taxiDetails = taxiRepository.save(taxiDetails);
        return taxiMapper.detailsToDTO(taxiDetails);
    }

    @Transactional
    public List<TaxiDetailsDTO> saveTaxis(List<AddTaxiRequest> addTaxiRequest) {
        var taxiDetails = addTaxiRequest.stream()
                .map(taxiMapper::requestToDetails)
                .toList();
        taxiDetails = taxiRepository.saveAll(taxiDetails);
        var taxisDTO = taxiDetails.stream().map(taxiMapper::detailsToDTO).toList();
        return taxisDTO;
    }
}
