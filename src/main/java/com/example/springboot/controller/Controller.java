package com.example.springboot.controller;

import com.example.springboot.dto.SearchCustomer;
import com.example.springboot.dto.SearchExpert;
import com.example.springboot.dto.SearchOrders;
import com.example.springboot.dto.customer.dtoCustomer;
import com.example.springboot.dto.expert.dtoExpert;
import com.example.springboot.dto.order.DtoOrder;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/find")
@RestController
public class Controller {

    private final SearchRepository search;

    @GetMapping("/home")
    public String hello(){
        return "Welcome to this Application !! ";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customer")
    public List<dtoCustomer> findCustomer(@RequestBody SearchCustomer request){
        final List<Customer> customers = search.findCustomerByCriteria(request);
        return ProductMapper.INSTANCE.customersToDtos(customers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/expert")
    public List<dtoExpert> findExpert(@RequestBody SearchExpert request) throws SubTasksException {
        final List<Expert> experts = search.findExpertByCriteria(request);
        return ProductMapper.INSTANCE.expertsToDtos(experts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public List<DtoOrder> findOrders(@RequestBody SearchOrders request) throws SubTasksException, CustomerException {
        final List<Orders> orders = search.findOrdersByCriteria(request);
        return ProductMapper.INSTANCE.ordersToDto(orders);
    }

}
