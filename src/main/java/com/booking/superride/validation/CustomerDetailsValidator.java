package com.booking.superride.validation;

import com.booking.superride.constants.AppConstants;
import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.exception.DuplicateDataException;
import com.booking.superride.repository.CustomerRepository;

public class CustomerDetailsValidator {

    private final CustomerRepository customerRepository;

    public CustomerDetailsValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validateCustomerDetails(CustomerDetailsDTO customerDetailsDTO) {

        var isCustomerPresent = customerRepository.existsByMobileNumber(customerDetailsDTO.mobileNumber());

        if (isCustomerPresent) {
            throw new DuplicateDataException(AppConstants.DUPLICATE_CUSTOMER_ERROR + customerDetailsDTO.mobileNumber());
        }
    }
}
