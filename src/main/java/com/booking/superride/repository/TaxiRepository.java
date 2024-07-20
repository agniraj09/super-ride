package com.booking.superride.repository;

import com.booking.superride.entity.TaxiDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRepository extends JpaRepository<TaxiDetails, Long> {

    List<TaxiDetails> findAllByStatus(String status);
}
