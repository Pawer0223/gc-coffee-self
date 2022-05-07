package com.example.gccoffee.controller;

import com.example.gccoffee.dto.CreateOrderDto;
import com.example.gccoffee.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody CreateOrderDto createdOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(createdOrderDto));
    }
}
