package com.example.springboot.services;

import com.example.springboot.dto.SaveOrder;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;

public interface OrderService {
    Orders saveOrder(SaveOrder orders) throws OrderException, CustomerException, SubTasksException;
}
