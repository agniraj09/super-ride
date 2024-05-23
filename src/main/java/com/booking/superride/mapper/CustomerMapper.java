package com.booking.superride.mapper;

import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.entity.CustomerDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDetails dtoToDetails(CustomerDetailsDTO customerDetailsDTO);
}
