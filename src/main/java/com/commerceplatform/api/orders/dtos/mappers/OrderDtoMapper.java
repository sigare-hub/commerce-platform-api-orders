package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.OrderModel;

public class OrderDtoMapper {
    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static OrderModel mapper(Customer customer, OrderDto dto) {
        return OrderModel.builder()
            .id(dto.getId())
            .orderItems(dto.getOrderItems())
            .total(dto.getTotal())
            .status(dto.getStatus())
            .orderPlacedIn(dto.getOrderPlacedIn())
            .build();
    }
}
