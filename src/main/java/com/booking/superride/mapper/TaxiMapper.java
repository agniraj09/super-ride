package com.booking.superride.mapper;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.entity.TaxiDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaxiMapper {

    @Mapping(target = "status", expression = "java(\"Active\")")
    TaxiDetails requestToDetails(AddTaxiRequest addTaxiRequest);

    TaxiDetailsDTO detailsToDTO(TaxiDetails taxiDetails);
}
