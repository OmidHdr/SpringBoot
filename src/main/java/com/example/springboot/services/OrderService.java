package com.example.springboot.services;

import com.example.springboot.entity.Orders;

public interface OrderService {
    Orders saveOrder(Orders orders);
}
