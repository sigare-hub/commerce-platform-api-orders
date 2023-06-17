package com.commerceplatform.api.orders.events.patterns.sagas;

import com.commerceplatform.api.orders.models.jpa.Order;
import com.commerceplatform.api.orders.repositories.OrderRepository;

public class OrderSagaActions {
    private final OrderRepository orderRepository;

    public OrderSagaActions(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private void cancelOrder(Order order) {
        orderRepository.delete(order);
    }
}
