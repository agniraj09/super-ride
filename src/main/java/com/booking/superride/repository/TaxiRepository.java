package com.booking.superride.repository;

import com.booking.superride.entity.TaxiDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxiRepository extends JpaRepository<TaxiDetails, Long> {

    List<TaxiDetails> findAllByStatus(String status);
}
