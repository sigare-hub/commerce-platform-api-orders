package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.models.jpa.OrderModel;

public class OrderItemDtoMapper {
    private OrderItemDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static OrderItem mapper(
            OrderItemDto orderItemDto,
            ProductDto productDto,
            OrderModel orderModel
    ) {
        return OrderItem.builder()
            .id(orderItemDto.getId())
            .price(orderItemDto.getPrice())
            .product(ProductDtoMapper.mapper(productDto))
            .order(orderModel)
            .quantity(orderItemDto.getQuantity())
            .build();
    }
}
