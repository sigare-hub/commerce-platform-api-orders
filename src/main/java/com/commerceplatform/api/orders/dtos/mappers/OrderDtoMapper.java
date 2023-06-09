package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.Order;
import com.commerceplatform.api.orders.models.jpa.Product;

import java.util.ArrayList;

public class OrderDtoMapper {

    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static Order mapper(OrderDto orderDto) {

        return Order.builder()
            .id(orderDto.getId())
            .total(orderDto.getTotal())
            .status(orderDto.getStatus())
            .orderPlacedIn(orderDto.getOrderPlacedIn())
            .build();
    }
}
