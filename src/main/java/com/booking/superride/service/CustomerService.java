package com.booking.superride.service;

import com.booking.superride.domain.CustomerDetailsDTO;
import com.booking.superride.entity.CustomerDetails;
import com.booking.superride.mapper.CustomerMapper;
import com.booking.superride.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDetails saveCustomerDetails(CustomerDetailsDTO customerDetailsDTO) {
        CustomerDetails details = customerMapper.dtoToDetails(customerDetailsDTO);
        return customerRepository.save(details);
    }
}
