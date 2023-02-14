package com.example.springboot.services;

import com.example.springboot.dto.SaveOrder;
import com.example.springboot.dto.ShowOrder;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;

import java.util.List;

public interface OrderService {
    Orders saveOrder(SaveOrder orders) throws OrderException, CustomerException, SubTasksException;
    List<ShowOrder> jobforExpert(SaveOrder order) throws OrderException, ExpertException, SubTasksException;
}
