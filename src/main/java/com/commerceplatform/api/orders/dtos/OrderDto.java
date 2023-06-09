package com.commerceplatform.api.orders.dtos;

import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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

    @JsonProperty("customer_id")
    private Long customerId;

    private OrderStatus status;

    @JsonProperty("order_placed_in")
    private LocalDateTime orderPlacedIn = LocalDateTime.now();

    @JsonProperty("order_items")
    private List<OrderItemDto> orderItems;
}
