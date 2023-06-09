package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.Order;
import com.commerceplatform.api.orders.models.jpa.Product;

public class OrderItemDtoMapper {
    private OrderItemDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static OrderItem mapper(
        OrderItemDto orderItemDto,
        Product product,
        Order order
    ) {
        return OrderItem.builder()
            .id(orderItemDto.getId())
            .price(orderItemDto.getPrice())
            .product(product)
            .order(order)
            .quantity(orderItemDto.getQuantity())
            .build();
    }
}
