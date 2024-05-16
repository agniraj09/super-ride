package com.booking.superride.service;

import com.booking.superride.domain.RideBookingRequest;
import com.booking.superride.domain.RideDetailsResponse;
import com.booking.superride.entity.RideDetails;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.entity.projections.AvailableTaxiFareDetails;
import com.booking.superride.entity.projections.OnTripTaxiDetails;
import com.booking.superride.mapper.RideDetailsMapper;
import com.booking.superride.repository.RideRepository;
import com.booking.superride.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final TaxiRepository taxiRepository;
    private final RideDetailsMapper rideDetailsMapper;

    public RideDetailsResponse bookRide(RideBookingRequest request) {
        TaxiDetails selectedTaxi;
        char pickupPoint = request.getPickupPoint();

        // Find all active taxis onboarded
        List<TaxiDetails> allActiveTaxis = taxiRepository.findAllByStatus("Active");
        if (CollectionUtils.isEmpty(allActiveTaxis)) {
            log.error("No active taxi found");
            return returnNoTaxiAvailableResponse();
        }

        // Find all taxis which are currently on trip and remove from active taxi list
        findAndRemoveOnTripTaxis(request, allActiveTaxis);
        if (CollectionUtils.isEmpty(allActiveTaxis)) {
            log.error("All taxis are on trip. No taxi is available");
            return returnNoTaxiAvailableResponse();
        }

        // Find minimum distance between taxi and customer
        final int minimumDistance = findMinimumDistance(allActiveTaxis, pickupPoint);

        // Select low earned taxi among all taxis available in minimum distance
        selectedTaxi = findMinimumEarnedTaxi(allActiveTaxis, pickupPoint, minimumDistance);
        if (selectedTaxi == null) {
            log.error("Available taxi cannot be found");
            return returnNoTaxiAvailableResponse();
        }
        selectedTaxi.setCurrentLocation(request.getDropPoint());
        taxiRepository.save(selectedTaxi);

        RideDetails rideDetails = buildRideDetails(request, selectedTaxi, pickupPoint);
        var bookedTaxi = rideRepository.save(rideDetails);
        return RideDetailsResponse.builder().status(HttpStatus.OK.getReasonPhrase()).message("Taxi booked.").rideDetailsDTO(rideDetailsMapper.detailsToDTO(bookedTaxi)).build();
    }

    private RideDetails buildRideDetails(RideBookingRequest request, TaxiDetails selectedTaxi, char pickupPoint) {
        int distanceBetweenPoints = Math.abs(request.getPickupPoint() - request.getDropPoint());
        int distanceToCover = distanceBetweenPoints * 15;
        double fare = calculateFare(distanceToCover);
        return RideDetails.builder().taxiId(selectedTaxi.getTaxiId()).rideDate(LocalDate.now()).customerId(request.getCustomerId()).pickupPoint(pickupPoint).dropPoint(request.getDropPoint()).pickupTime(request.getPickupTime()).dropTime(request.getPickupTime().plusMinutes(distanceBetweenPoints * 60)).fare(fare).distance(distanceToCover).build();
    }

    private TaxiDetails findMinimumEarnedTaxi(List<TaxiDetails> allActiveTaxis, char pickupPoint, int minimumDistance) {
        TaxiDetails selectedTaxi;
        var availableTaxis = allActiveTaxis.stream().filter(taxi -> Math.abs(taxi.getCurrentLocation() - pickupPoint) == minimumDistance).toList();
        List<AvailableTaxiFareDetails> availableTaxiFareDetails = rideRepository.sumByRideDateAndTaxiIdIn(LocalDate.now(), availableTaxis.stream().map(TaxiDetails::getTaxiId).toList());
        log.info("availableTaxiFareDetails -> {}", availableTaxiFareDetails);
        if (CollectionUtils.isEmpty(availableTaxiFareDetails)) {
            selectedTaxi = availableTaxis.get(0);
        } else {
            var selectedTaxiId = availableTaxiFareDetails.stream().filter(taxi -> Objects.nonNull(taxi.getFare())).min(Comparator.comparingDouble(AvailableTaxiFareDetails::getFare)).get().getTaxiId();
            selectedTaxi = availableTaxis.stream().filter(taxi -> Objects.equals(selectedTaxiId, taxi.getTaxiId())).findAny().get();
        }
        return selectedTaxi;
    }

    private static int findMinimumDistance(List<TaxiDetails> allActiveTaxis, char pickupPoint) {
        int shortestDistance = Integer.MAX_VALUE;
        for (var availableTaxi : allActiveTaxis) {
            int distanceDifference = Math.abs(availableTaxi.getCurrentLocation() - pickupPoint);
            if (distanceDifference < shortestDistance) {
                shortestDistance = distanceDifference;
            }
        }
        return shortestDistance;
    }

    private void findAndRemoveOnTripTaxis(RideBookingRequest request, List<TaxiDetails> allActiveTaxis) {
        List<OnTripTaxiDetails> onTripTaxiDetails = rideRepository.findAllByRideDateAndDropTimeGreaterThanEqual(request.getPickupTime().toLocalDate(), request.getPickupTime());
        List<Long> onTripTaxiIds = onTripTaxiDetails.stream().map(OnTripTaxiDetails::getTaxiId).toList();
        allActiveTaxis.removeIf(taxi -> onTripTaxiIds.contains(taxi.getTaxiId()));
    }

    private static RideDetailsResponse returnNoTaxiAvailableResponse() {
        return RideDetailsResponse.builder().status(HttpStatus.NOT_FOUND.getReasonPhrase()).message("All taxis are currently booked. Please try again later!").build();
    }

    private double calculateFare(int distanceToRide) {
        double fare;
        if (distanceToRide > 5) {
            // 100 for first 5 KMs and then 10 for each KM
            fare = 100 + ((distanceToRide - 5) * 10);

        } else {
            fare = distanceToRide * 100;
        }
        return fare;
    }
}
