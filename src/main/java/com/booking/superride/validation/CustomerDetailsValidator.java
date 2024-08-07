package com.booking.superride.validation;

import com.booking.superride.constants.AppConstants;
import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.exception.DuplicateDataException;
import com.booking.superride.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class CustomerDetailsValidator {

    private final CustomerRepository customerRepository;

    CustomerDetailsValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateCustomerDetails(CustomerDetailsDTO customerDetailsDTO) {

        var isCustomerPresent = customerRepository.existsByMobileNumber(customerDetailsDTO.mobileNumber());

        if (isCustomerPresent) {
            throw new DuplicateDataException(AppConstants.DUPLICATE_CUSTOMER_ERROR + customerDetailsDTO.mobileNumber());
        }
    }
}
