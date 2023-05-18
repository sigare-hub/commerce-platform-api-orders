package com.commerceplatform.api.orders.dtos;

import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.models.jpa.OrderItem;
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
    private LocalDateTime orderPlacedIn = LocalDateTime.now();
    private List<OrderItem> orderItems;
}
