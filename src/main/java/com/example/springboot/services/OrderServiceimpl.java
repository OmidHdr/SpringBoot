package com.example.springboot.services;

import com.example.springboot.dto.SaveOffer;
import com.example.springboot.dto.SaveOrder;
import com.example.springboot.dto.ShowOrder;
import com.example.springboot.entity.*;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.ExpertException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.exeption.SubTasksException;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderServiceimpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final SubTaskServices subTaskServices;
    private final ExpertServiceimpl expertService;
    private final OfferService offerService;

    public OrderServiceimpl(OrderRepository orderRepository, CustomerService customerService, SubTaskServices subTaskServices, ExpertServiceimpl expertService, OfferService offerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subTaskServices = subTaskServices;
        this.expertService = expertService;
        this.offerService = offerService;
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

    //section check expert exist
    public boolean expertAccess(List<SubTasks> listSubTask, SubTasks subTask) {
        AtomicBoolean flag = new AtomicBoolean(false);
        listSubTask.forEach(subTasks -> {
            if (Objects.equals(subTasks.getName(), subTask.getName()))
                flag.set(true);
        });
        return flag.get();
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
        if (!expertAccess(subExpert, subtask))
            throw new OrderException("you do not have access to see this result ");
        List<ShowOrder> result = new ArrayList<>();
        listOrders.forEach(orders -> {
            ShowOrder showOrder = ShowOrder.builder()
                    .address(orders.getAddress())
                    .price(orders.getProposedPrice())
                    .description(orders.getDescription())
                    .date(orders.getStartDate())
                    .id(orders.getId())
                    .build();
            result.add(showOrder);
        });
        return result;
    }

    //section confirm job
    @Override
    public ShowOrder confirmJob(SaveOffer offer) throws OrderException, ExpertException, SubTasksException {
        if (offer.getUsername() == null || offer.getPassword() == null || offer.getSubtaskName() == null || offer.getOfferId() == null)
            throw new OrderException("you should fill all of the items");

        Expert expert = expertService.findByUsernameAndPassword(offer.getUsername(), offer.getPassword());
        SaveOrder saveOrder = SaveOrder.builder()
                        .username(offer.getUsername()).password(offer.getPassword())
                        .subtaskName(offer.getSubtaskName()).build();
        final List<ShowOrder> showOrders = jobforExpert(saveOrder);
        for (int i = 0; i < showOrders.size(); i++) {
            if (Objects.equals(showOrders.get(i).getId(), offer.getOfferId())){
                final Optional<Orders> byId = orderRepository.findById(offer.getOfferId());
                final Orders orders = byId.get();
                Offers offers = Offers.builder()
                                .suggestion(offer.getSuggestion()).date(offer.getTimeStart())
                                .periodOfTime(offer.getPeriodOfTime()).expert(expert).orders(orders)
                        .build();
                orders.setOffer(Collections.singletonList(offers));
                offerService.saveOffers(offers);
                return showOrders.get(i);
            }
        }
        throw new OrderException("failed to do");
    }
    public Optional<Orders> findById(Long offerId) throws OrderException {
        final Optional<Orders> byId = orderRepository.findById(offerId);
        if (!byId.isPresent())
            throw new OrderException("wrong id");
        return byId;
    }

}
