package com.commerceplatform.api.orders.controllers;

import com.commerceplatform.api.orders.models.jpa.OrderItem;
import com.commerceplatform.api.orders.services.OrderDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Optional<List<OrderItem>>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        Optional<List<OrderItem>> orderItems = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderItems);
    }
}