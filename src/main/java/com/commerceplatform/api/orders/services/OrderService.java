package com.commerceplatform.api.orders.services;

import com.commerceplatform.api.orders.enums.OrderStatus;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderModel createOrder(Long customerId, List<Integer> productIds) {
        var order = new OrderModel();
        order.setStatus(OrderStatus.ORDER_PLACED);
        return orderRepository.save(order);
    }
}
