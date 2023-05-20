package com.commerceplatform.api.orders.dtos;

import com.commerceplatform.api.orders.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private double total;
    private Long customer;
    private OrderStatus status;

    @JsonProperty("order_place_in")
    private LocalDateTime orderPlacedIn = LocalDateTime.now();

    @JsonProperty("order_items")
    private List<OrderItemDto> orderItems;
}
