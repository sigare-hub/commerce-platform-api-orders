package com.commerceplatform.api.orders.controllers;

import com.commerceplatform.api.orders.dtos.OrderDto;
import com.commerceplatform.api.orders.exceptions.NotFoundException;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.repositories.ProductRepository;
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
    private final ProductRepository productRepository;

    public OrderController(OrderService orderService,
                           ProductRepository productRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @PostMapping
    public ResponseEntity<OrderModel> create(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }
}
