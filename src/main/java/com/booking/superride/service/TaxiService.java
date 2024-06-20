package com.booking.superride.service;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.mapper.TaxiMapper;
import com.booking.superride.repository.TaxiRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var taxiDetails = taxiMapper.requestToDetailsList(addTaxiRequest);
        taxiDetails = taxiRepository.saveAll(taxiDetails);
        return taxiMapper.detailsToDTOList(taxiDetails);
    }
}
