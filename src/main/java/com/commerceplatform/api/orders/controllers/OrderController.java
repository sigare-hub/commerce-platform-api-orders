package com.commerceplatform.api.orders.controllers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderModel> create(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(orderDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> findById(@PathVariable Long id) {
        Optional<OrderModel> order = orderService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order.get());
    }

    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.findAll();
    }
}
