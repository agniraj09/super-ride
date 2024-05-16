package com.booking.superride.repository;

import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.entity.projections.TaxiCurrentLocationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaxiRepository extends JpaRepository<TaxiDetails, Long> {

    TaxiDetails save(TaxiDetails taxiDetails);
    List<TaxiDetails> findAllByStatus(String status);

    @Query(value = "SELECT taxiid, currentlocation from taxi_details where taxiid in :taxiIds", nativeQuery = true)
    List<TaxiCurrentLocationDetails> findAllByTaxiId(@Param("taxiIds") List<Long> taxiIds);
}
