package com.commerceplatform.api.orders.dtos;

import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("order_id")
    private Long orderId;

    private int quantity;

    private double price;

    public OrderItemDto(List<OrderItem> orderItems) {
    }
}
