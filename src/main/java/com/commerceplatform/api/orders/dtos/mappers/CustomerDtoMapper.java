package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.CustomerDto;
import com.commerceplatform.api.orders.models.jpa.Customer;

public class CustomerDtoMapper {
    private CustomerDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static Customer mapper(CustomerDto customerDto) {
        return Customer.builder()
            .id(customerDto.getId())
            .build();
    }

}