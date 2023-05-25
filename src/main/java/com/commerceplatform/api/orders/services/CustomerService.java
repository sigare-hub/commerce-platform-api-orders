package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.dtos.CustomerDto;
import com.commerceplatform.api.orders.dtos.mappers.CustomerDtoMapper;
import com.commerceplatform.api.orders.exceptions.BadRequestException;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CustomerDto input) {
        try {
            return customerRepository.save(CustomerDtoMapper.mapper(input));
        } catch (Exception e) {
            throw new BadRequestException("Unable to create customer");
        }
    }
}
