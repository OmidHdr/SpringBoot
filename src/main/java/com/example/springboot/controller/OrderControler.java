package com.example.springboot.controller;

import com.example.springboot.dto.SaveOffer;
import com.example.springboot.dto.SaveOrder;
import com.example.springboot.dto.ShowOrder;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.services.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @PostMapping("/jobforExpert")
    public List<ShowOrder> jobForExpert(@RequestBody SaveOrder order) throws ExpertException, SubTasksException, OrderException {
        return orderService.jobforExpert(order);
    }

    @PostMapping("/confirmJob")
    public ShowOrder confirmJob(@RequestBody SaveOffer offer) throws ExpertException, SubTasksException, OrderException {
        return orderService.confirmJob(offer);
    }
}
