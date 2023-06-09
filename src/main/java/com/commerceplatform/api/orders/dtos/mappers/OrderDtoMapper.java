package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.Order;

public class OrderDtoMapper {

    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static Order mapper(OrderDto orderDto, Customer customer) {

        return Order.builder()
            .id(orderDto.getId())
            .total(orderDto.getTotal())
            .status(orderDto.getStatus())
            .orderPlacedIn(orderDto.getOrderPlacedIn())
            .customer(customer)
            .build();
    }
}
