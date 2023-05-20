package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;

import java.util.List;

public class OrderDtoMapper {
    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static OrderModel mapper(OrderDto orderDto, List<OrderItem> orderItems) {
        return OrderModel.builder()
            .id(orderDto.getId())
            .orderItems(orderItems)
            .total(orderDto.getTotal())
            .status(orderDto.getStatus())
            .orderPlacedIn(orderDto.getOrderPlacedIn())
            .build();
    }
}
