package com.commerceplatform.api.orders.dtos.inputs;

import com.commerceplatform.api.orders.dtos.OrderItemDto;

import java.util.Set;

public record CreateOrderInput(Long customerId, Set<OrderItemDto> orderItems) {}
