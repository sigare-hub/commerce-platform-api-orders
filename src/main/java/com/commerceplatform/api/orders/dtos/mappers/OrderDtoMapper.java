package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.dtos.OrderItemDto;
import com.commerceplatform.api.orders.models.jpa.Customer;
import com.commerceplatform.api.orders.models.jpa.Order;

import java.util.List;

public class OrderDtoMapper {

    private OrderDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static Order toEntity(OrderDto orderDto) {
        var customer = new Customer();
        customer.setId(orderDto.getCustomerId());

        return Order.builder()
            .id(orderDto.getId())
            .total(orderDto.getTotal())
            .status(orderDto.getStatus())
            .orderPlacedIn(orderDto.getOrderPlacedIn())
            .customer(customer)
            .build();
    }

    public static OrderDto toDto(Order order, List<OrderItemDto> orderItemsDto) {
        var validCustomer = order.getCustomer() != null ? order.getCustomer().getId() : null;
        return OrderDto.builder()
            .id(order.getId())
            .total(order.getTotal())
            .customerId(validCustomer)
            .status(order.getStatus())
            .orderPlacedIn(order.getOrderPlacedIn())
            .orderItems(orderItemsDto)
            .build();
    };
}
