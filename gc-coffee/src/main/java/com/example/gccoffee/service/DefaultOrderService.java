package com.example.gccoffee.service;

import com.example.gccoffee.dao.orders.OrderRepository;
import com.example.gccoffee.dto.CreateOrderDto;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long createOrder(CreateOrderDto createOrderDto) {
        return orderRepository.insert(createOrderDto);
    }

}
