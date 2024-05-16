package com.booking.superride.mapper;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.entity.TaxiDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxiMapper {
    TaxiDetails requestToDetails(AddTaxiRequest addTaxiRequest);
    TaxiDetailsDTO detailsToDTO(TaxiDetails taxiDetails);
}
