package com.example.gccoffee.dao.orders;

import com.example.gccoffee.dto.CreateOrderDto;
import com.example.gccoffee.model.Order;

import java.util.Optional;

public interface OrderRepository {
    Long insert(CreateOrderDto createOrderDto);

    Optional<Order> findById(Long orderId);
}
