package com.booking.superride.mapper;

import com.booking.superride.domain.AddTaxiRequest;
import com.booking.superride.domain.TaxiDetailsDTO;
import com.booking.superride.entity.TaxiDetails;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaxiMapper {

    @Mapping(target = "status", expression = "java(\"Active\")")
    TaxiDetails requestToDetails(AddTaxiRequest addTaxiRequest);

    List<TaxiDetails> requestToDetailsList(List<AddTaxiRequest> addTaxiRequest);

    TaxiDetailsDTO detailsToDTO(TaxiDetails taxiDetails);

    List<TaxiDetailsDTO> detailsToDTOList(List<TaxiDetails> taxiDetails);
}
