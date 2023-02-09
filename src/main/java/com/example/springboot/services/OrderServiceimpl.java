package com.example.springboot.services;

import com.example.springboot.entity.Orders;
import com.example.springboot.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceimpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceimpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Orders saveOrder(Orders orders) {
        return orderRepository.save(orders);
    }

}
