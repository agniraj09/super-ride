package com.booking.superride.service;

import static com.booking.superride.constants.AppConstants.*;

import com.booking.superride.domain.RideBookingRequest;
import com.booking.superride.domain.RideDetailsResponse;
import com.booking.superride.entity.RideDetails;
import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.entity.projections.AvailableTaxiFareDetails;
import com.booking.superride.entity.projections.OnTripTaxiDetails;
import com.booking.superride.exception.TaxiNotFoundException;
import com.booking.superride.mapper.RideDetailsMapper;
import com.booking.superride.repository.RideRepository;
import com.booking.superride.repository.TaxiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RideService {
    private final RideRepository rideRepository;
    private final TaxiRepository taxiRepository;
    private final RideDetailsMapper rideDetailsMapper;
    private final TransactionTemplate transactionTemplate;

    public RideService(RideRepository rideRepository,
                       TaxiRepository taxiRepository,
                       RideDetailsMapper rideDetailsMapper,
                       TransactionTemplate transactionTemplate) {
        this.rideRepository = rideRepository;
        this.taxiRepository = taxiRepository;
        this.rideDetailsMapper = rideDetailsMapper;
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.transactionTemplate = transactionTemplate;
    }


    public RideDetailsResponse bookRide(RideBookingRequest request) {
        TaxiDetails selectedTaxi;
        char pickupPoint = request.pickupPoint();

        // Find all active taxis onboarded
        List<TaxiDetails> allActiveTaxis = taxiRepository.findAllByStatus("Active");
        if (CollectionUtils.isEmpty(allActiveTaxis)) {
            throw new TaxiNotFoundException(NO_ACTIVE_TAXI_FOUND_ERROR);
        }

        // Find all taxis which are currently on trip and remove from active taxi list
        findAndRemoveOnTripTaxis(request, allActiveTaxis);
        if (CollectionUtils.isEmpty(allActiveTaxis)) {
            throw new TaxiNotFoundException(ALL_TAXIS_ARE_IN_TRIP_ERROR);
        }

        // Find minimum distance between taxi and customer
        final int minimumDistance = findMinimumDistance(allActiveTaxis, pickupPoint);

        // Select low earned taxi among all taxis available in minimum distance
        selectedTaxi = findMinimumEarnedTaxi(allActiveTaxis, pickupPoint, minimumDistance);
        if (selectedTaxi == null) {
            throw new TaxiNotFoundException(GENERIC_TAXI_NOT_FOUND_ERROR);
        }
        selectedTaxi.setCurrentLocation(request.dropPoint());

        var bookedTaxi = transactionTemplate.execute(action -> {
            taxiRepository.save(selectedTaxi);
            RideDetails rideDetails = buildRideDetails(request, selectedTaxi);
            return rideRepository.save(rideDetails);
        });

        return new RideDetailsResponse(
                HttpStatus.OK.getReasonPhrase(),
                "Taxi booked.",
                rideDetailsMapper.detailsToDTO(bookedTaxi));
    }

    private RideDetails buildRideDetails(RideBookingRequest request, TaxiDetails selectedTaxi) {
        int distanceBetweenPoints = Math.abs(request.pickupPoint() - request.dropPoint());
        double fare = calculateFare(distanceBetweenPoints * 15);
        return rideDetailsMapper.rideBookingRequestToRideDetails(request, selectedTaxi.getTaxiId(), fare);
    }

    private TaxiDetails findMinimumEarnedTaxi(List<TaxiDetails> allActiveTaxis, char pickupPoint, int minimumDistance) {
        TaxiDetails selectedTaxi;
        var availableTaxis = allActiveTaxis.stream()
                .filter(taxi -> Math.abs(taxi.getCurrentLocation() - pickupPoint) == minimumDistance)
                .toList();
        var availableTaxiIds = availableTaxis.stream().map(TaxiDetails::getTaxiId).toList();
        List<AvailableTaxiFareDetails> availableTaxiFareDetails =
                rideRepository.sumByRideDateAndTaxiIdIn(LocalDate.now(), availableTaxiIds);
        log.info("availableTaxiFareDetails -> {}", availableTaxiFareDetails);
        if (CollectionUtils.isEmpty(availableTaxiFareDetails)) {
            selectedTaxi = availableTaxis.get(0);
        } else {
            var selectedTaxiId = availableTaxiFareDetails.stream()
                    .min(Comparator.comparingDouble(AvailableTaxiFareDetails::getFare))
                    .get()
                    .getTaxiId();
            selectedTaxi = availableTaxis.stream().filter(taxi -> Objects.equals(selectedTaxiId, taxi.getTaxiId())).findAny().get();
        }
        return selectedTaxi;
    }

    private static int findMinimumDistance(List<TaxiDetails> allActiveTaxis, char pickupPoint) {
        return allActiveTaxis.stream()
                .mapToInt(taxi -> Math.abs(taxi.getCurrentLocation() - pickupPoint))
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    private void findAndRemoveOnTripTaxis(RideBookingRequest request, List<TaxiDetails> allActiveTaxis) {
        List<OnTripTaxiDetails> onTripTaxiDetails =
                rideRepository.findAllByRideDateAndDropTimeGreaterThanEqual(request.pickupTime().toLocalDate(),
                        request.pickupTime());
        List<Long> onTripTaxiIds = onTripTaxiDetails.stream().map(OnTripTaxiDetails::getTaxiId).toList();
        allActiveTaxis.removeIf(taxi -> onTripTaxiIds.contains(taxi.getTaxiId()));
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
