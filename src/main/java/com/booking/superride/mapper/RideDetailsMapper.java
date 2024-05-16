package com.booking.superride.mapper;

import com.booking.superride.domain.RideDetailsDTO;
import com.booking.superride.entity.RideDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RideDetailsMapper {

    RideDetailsDTO detailsToDTO(RideDetails rideDetails);
}
