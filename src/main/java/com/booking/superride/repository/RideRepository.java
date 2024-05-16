package com.booking.superride.repository;

import com.booking.superride.entity.RideDetails;
import com.booking.superride.entity.projections.AvailableTaxiFareDetails;
import com.booking.superride.entity.projections.OnTripTaxiDetails;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<RideDetails, Long> {

    RideDetails save(RideDetails rideDetails);
    List<OnTripTaxiDetails> findAllByRideDateAndDropTimeGreaterThanEqual(LocalDate date, LocalDateTime dateTime);

    @Query(value = "SELECT taxiid as TaxiId, sum(fare) as fare from ride_details where ridedate = :rideDate and taxiid in :taxiids group by taxiid", nativeQuery = true)
    List<AvailableTaxiFareDetails> sumByRideDateAndTaxiIdIn(@Param("rideDate") LocalDate rideDate,@Param("taxiids") List<Long> taxiids);
}
