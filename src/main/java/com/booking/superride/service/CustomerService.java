package com.booking.superride.service;

import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.mapper.CustomerMapper;
import com.booking.superride.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDetails saveCustomerDetails(CustomerDetailsDTO customerDetailsDTO) {
        CustomerDetails details = customerMapper.dtoToDetails(customerDetailsDTO);
        return customerRepository.save(details);
    }
}
