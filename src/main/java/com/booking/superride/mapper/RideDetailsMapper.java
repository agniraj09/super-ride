package com.booking.superride.mapper;

import com.booking.superride.domain.RideBookingRequest;
import com.booking.superride.domain.RideDetailsDTO;
import com.booking.superride.entity.RideDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RideDetailsMapper {

    RideDetailsDTO detailsToDTO(RideDetails rideDetails);

    @Mappings({
        @Mapping(
                target = "dropTime",
                expression =
                        "java(request.pickupTime().plusMinutes((Math.abs(request.pickupPoint() - request.dropPoint())) * 60))"),
        @Mapping(
                target = "distance",
                expression = "java((Math.abs(request.pickupPoint() - request.dropPoint())) * 15)"),
        @Mapping(target = "rideDate", expression = "java(LocalDate.now())"),
        @Mapping(target = "taxiId", source = "taxiId"),
        @Mapping(target = "fare", source = "fare")
    })
    RideDetails rideBookingRequestToRideDetails(RideBookingRequest request, Long taxiId, Double fare);
}
