package com.booking.superride.repository;

import com.booking.superride.entity.TaxiDetails;
import com.booking.superride.entity.projections.TaxiNumberDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaxiRepository extends JpaRepository<TaxiDetails, Long> {

    List<TaxiDetails> findAllByStatus(String status);

    @Query(value = "SELECT taxiNumber as taxiNumber from TaxiDetails where taxiNumber in :taxiNumbers")
    List<TaxiNumberDetails> findByTaxiNumberIn(@Param("taxiNumbers") List<String> taxiNumbers);
}
