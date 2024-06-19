package com.booking.superride.repository;

import com.booking.superride.entity.RideDetails;
import com.booking.superride.entity.projections.AvailableTaxiFareDetails;
import com.booking.superride.entity.projections.OnTripTaxiDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RideRepository extends JpaRepository<RideDetails, Long> {

    List<OnTripTaxiDetails> findAllByRideDateAndDropTimeGreaterThanEqual(
            LocalDate date, LocalDateTime dateTime);

    @Query(
            value =
                    "SELECT taxiId as taxiId, sum(fare) as fare from RideDetails where rideDate = :rideDate and taxiId in :taxiids group by taxiId")
    List<AvailableTaxiFareDetails> sumByRideDateAndTaxiIdIn(
            @Param("rideDate") LocalDate rideDate, @Param("taxiids") List<Long> taxiids);
}
