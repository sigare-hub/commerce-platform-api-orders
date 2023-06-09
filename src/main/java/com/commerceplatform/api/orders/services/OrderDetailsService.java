package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderItemRepository orderItemRepository;

    public OrderDetailsService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
}
