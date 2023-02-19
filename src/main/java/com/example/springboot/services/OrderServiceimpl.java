package com.example.springboot.services;

import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.*;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.exeption.*;
import com.example.springboot.mapper.ProductMapper;
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
    public void saveOrder(OrderSave order) throws OrderException, CustomerException, SubTasksException {
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
        orderRepository.save(orders);
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
    public List<OrderShow> jobforExpert(OrderSave order) throws OrderException, ExpertException, SubTasksException {
        if (order.getUsername() == null || order.getPassword() == null || order.getSubtaskName() == null)
            throw new OrderException("you should fill all of the items");
        final Expert expert = expertService.findByUsernameAndPassword(order.getUsername(), order.getPassword());
        final SubTasks subtask = subTaskServices.findByName(order.getSubtaskName());
        final List<Orders> listOrders = orderRepository.findByJobStatusAndSubTasks(JobStatus.WAITING_FOR_EXPERT, subtask);
        if (listOrders == null || listOrders.size() < 1)
            throw new OrderException("you can not send offer for this order because " +
                    "the status is not waiting for expert");
        final List<SubTasks> subExpert = expert.getSubTasks();
        if (!expertAccess(subExpert, subtask))
            throw new OrderException("you do not have access to see this result ");
        List<OrderShow> result = new ArrayList<>();
        listOrders.forEach(orders -> {
            OrderShow orderShow = OrderShow.builder()
                    .address(orders.getAddress())
                    .price(orders.getProposedPrice())
                    .description(orders.getDescription())
                    .date(orders.getStartDate())
                    .id(orders.getId())
                    .build();
            result.add(orderShow);
        });
        return result;
    }

    //section confirm job
    @Override
    public OrderShow confirmJob(OffersSave offer) throws OrderException, ExpertException, SubTasksException {
        if (offer.getUsername() == null || offer.getPassword() == null || offer.getSubtaskName() == null || offer.getOrderId() == null)
            throw new OrderException("you should fill all of the items");

        Expert expert = expertService.findByUsernameAndPassword(offer.getUsername(), offer.getPassword());
        OrderSave orderSave = OrderSave.builder()
                .username(offer.getUsername()).password(offer.getPassword())
                .subtaskName(offer.getSubtaskName()).build();
        final Orders order = orderRepository.findById(offer.getOrderId()).get();
        final List<OrderShow> orderShows = jobforExpert(orderSave);
        for (int i = 0; i < orderShows.size(); i++) {
            if (Objects.equals(orderShows.get(i).getId(), offer.getOrderId())) {
                final Orders orders = orderRepository.findById(offer.getOrderId()).get();
                if (orders.getJobStatus() != JobStatus.WAITING_FOR_EXPERT)
                    throw new OrderException("you can not send offer for this order because " +
                            "the status is not waiting for expert");
                Offers offers = Offers.builder()
                        .suggestion(offer.getSuggestion()).date(offer.getTimeStart())
                        .periodOfTime(offer.getPeriodOfTime()).expert(expert).orders(order)
                        .build();
                orders.setOffer(Collections.singletonList(offers));
                offerService.saveOffers(offers);
                return orderShows.get(i);
            }
        }
        throw new OrderException("failed to do");
    }

    @Override
    public Optional<Orders> findById(Long offerId) throws OrderException {
        final Optional<Orders> byId = orderRepository.findById(offerId);
        if (!byId.isPresent())
            throw new OrderException("wrong id");
        return byId;
    }

    //section get all suggestion
    @Override
    public List<OffersSet> getAllExpertSuggestions(OffersGet offer) throws OfferException, CustomerException, SubTasksException, OrderException {
        // check offer is not null
        if (offer.getUsername() == null || offer.getPassword() == null ||
                offer.getSubtaskName() == null || offer.getOrderId() == null)
            throw new OfferException("you should fill all of the items ");
        // find customer
        final Customer customer = customerService.findByUsernameAndPassword(offer.getUsername(), offer.getPassword());
        // task
        final SubTasks subtask = subTaskServices.findByName(offer.getSubtaskName());
        // find order
        final Optional<Orders> order = orderRepository.findById(offer.getOrderId());
        if (!Objects.equals(order.get().getCustomer().getId(), customer.getId()))
            throw new OrderException("sorry you cant see this result");
        // find offer
        final List<Offers> offers = offerService.findByOrders(offer.getOrderId());
        if (offers == null)
            throw new OrderException("there is no order");
        final List<OffersSet> result = ProductMapper.INSTANCE.offerToDtoList(offers);
        return result;
    }

    // section confirm Order
    @Override
    public OrderShow confirmOrder(Long idOrder , Long idOffer) throws OrderException, OfferException {
        if (idOrder == null || idOffer == null)
            throw new OrderException("you should fill all of the items");
        Orders order = orderRepository.findById(idOrder).get();
        final Offers offer = offerService.findById(idOffer);
        order.setJobStatus(JobStatus.EXPERT_ON_WAY);
        offer.setStatus(true);
        final OrderShow result = ProductMapper.INSTANCE.orderTodto(order);
        result.setPrice(offer.getSuggestion());
        result.setDate(offer.getDate());
        offerService.saveOffers(offer);
        orderRepository.save(order);
        return result;
    }

    //section start work
    @Override
    public OrderShow startWork(Long id) throws OrderException {
        Orders order = orderRepository.findById(id).get();
        if (order.getJobStatus() != JobStatus.EXPERT_ON_WAY)
            throw new OrderException("the order is not in expert on way situation");
        order.setJobStatus(JobStatus.STARTED);
        orderRepository.save(order);
        return ProductMapper.INSTANCE.orderTodto(order);
    }


}
