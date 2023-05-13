package com.commerceplatform.api.orders.controllers;

import com.commerceplatform.api.orders.integrations.api.products.dtos.ProductDto;
import com.commerceplatform.api.orders.models.jpa.OrderModel;
import com.commerceplatform.api.orders.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderModel> createOrder() {

        List<Long> dto = new ArrayList<>();
        Long customerId = 3L;
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createOrder(customerId, dto));
    }
}
