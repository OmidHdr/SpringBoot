package com.example.springboot.controller;

import com.example.springboot.dto.SaveOrder;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControler {

    private final OrderService orderService;

    public OrderControler(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/sendOrder")
    public Orders sendOrder(@RequestBody SaveOrder order) throws CustomerException, SubTasksException, OrderException {
        return orderService.saveOrder(order);
    }

}
