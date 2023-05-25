package com.commerceplatform.api.orders.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("order_id")
    @Nullable()
    private Long orderId;

    private int quantity;

    private double price;
}
