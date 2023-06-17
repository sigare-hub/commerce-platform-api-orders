package com.commerceplatform.api.orders.controllers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.models.jpa.Order;
import com.commerceplatform.api.orders.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(input));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @PatchMapping
    public ResponseEntity<OrderDto> updateOrderStatus(@RequestBody OrderDto input) {
        orderService.updateOrderStatus(input);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
