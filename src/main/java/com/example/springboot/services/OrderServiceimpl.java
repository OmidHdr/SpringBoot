package com.example.springboot.services;

import com.example.springboot.dto.SaveOrder;
import com.example.springboot.dto.ShowOrder;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.Orders;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderServiceimpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final SubTaskServices subTaskServices;
    private final ExpertServiceimpl expertService;

    public OrderServiceimpl(OrderRepository orderRepository, CustomerService customerService, SubTaskServices subTaskServices, ExpertServiceimpl expertService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subTaskServices = subTaskServices;
        this.expertService = expertService;
    }

    //section save order
    @Override
    public Orders saveOrder(SaveOrder order) throws OrderException, CustomerException, SubTasksException {
        if (order.getUsername() == null || order.getPassword() == null ||
                order.getDescription() == null || order.getSubtaskName() == null ||
                order.getPriceSuggestion() == null || order.getAddress() == null)
            throw new OrderException("you should fill all of the items");
        Customer customer = customerService.findByUsernameAndPassword(order.getUsername(), order.getPassword());
        SubTasks subtask = subTaskServices.findByName(order.getSubtaskName());
        if (order.getPriceSuggestion() < subtask.getBasePrice())
            throw new OrderException("you can not send order price less than base price edit it and try again");
        if (!Validation.validDate(order.getStartDate()))
            throw new OrderException("wrong date you should fill it this way '1401-01-01' and date can not be before today");
        Orders orders = Orders.builder().customer(customer)
                .description(order.getDescription())
                .proposedPrice(order.getPriceSuggestion())
                .startDate(order.getStartDate())
                .address(order.getAddress())
                .jobStatus(JobStatus.WAITING_FOR_EXPERT)
                .subTasks(subtask).build();
        return orderRepository.save(orders);
    }

    //section job for expert
    @Override
    public List<ShowOrder> jobforExpert(SaveOrder order) throws OrderException, ExpertException, SubTasksException {
        if (order.getUsername() == null || order.getPassword() == null || order.getSubtaskName() == null)
            throw new OrderException("you should fill all of the items");
        final Expert expert = expertService.findByUsernameAndPassword(order.getUsername(), order.getPassword());
        final SubTasks subtask = subTaskServices.findByName(order.getSubtaskName());
        final List<Orders> listOrders = orderRepository.findByJobStatusAndSubTasks(JobStatus.WAITING_FOR_EXPERT, subtask);
        if (listOrders == null)
            throw new OrderException("there is no job ");
        final List<SubTasks> subExpert = expert.getSubTasks();
        AtomicBoolean flag = new AtomicBoolean(false);
        subExpert.forEach(subTasks -> {
            if (Objects.equals(subtask.getName(), subTasks.getName()))
                flag.set(true);
        });
        if (!flag.get())
            throw new OrderException("you can't see ");
        List<ShowOrder> result = new ArrayList<>();
        listOrders.forEach(orders -> {
            ShowOrder showOrder = ShowOrder.builder()
                    .address(orders.getAddress())
                    .price(orders.getProposedPrice())
                    .description(orders.getDescription())
                    .date(orders.getStartDate()).build();
            result.add(showOrder);
        });
        return result;

    }

}
