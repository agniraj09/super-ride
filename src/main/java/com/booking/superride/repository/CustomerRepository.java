package com.booking.superride.repository;

import com.booking.superride.entity.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerDetails, Long> {

    boolean existsByMobileNumber(String mobileNumber);
}
