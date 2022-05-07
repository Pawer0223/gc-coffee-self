package com.example.gccoffee.service;

import com.example.gccoffee.dto.CreateOrderDto;

public interface OrderService {
  Long createOrder(CreateOrderDto createOrderDto);
}
