package com.example.springboot.controller;

import com.example.springboot.entity.Orders;
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

    @PostMapping("/order")
    public Orders sendOrder(@RequestBody Orders orders){
        return orderService.saveOrder(orders);
    }

}
