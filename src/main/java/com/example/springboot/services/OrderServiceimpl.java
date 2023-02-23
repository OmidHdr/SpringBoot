package com.example.springboot.services;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.*;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.exeption.*;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.repository.OfferRepository;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderServiceimpl implements OrderService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OfferService offerService;
    private final OrderRepository orderRepository;
    private final SubTaskServices subTaskServices;
    private final ExpertServiceimpl expertService;
    @Autowired
    private OfferRepository offerRepository;

    public OrderServiceimpl(OrderRepository orderRepository, CustomerService customerService, SubTaskServices subTaskServices, ExpertServiceimpl expertService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subTaskServices = subTaskServices;
        this.expertService = expertService;
    }

    //section save Order
    @Override
    public void saveOrder(OrderSave dtoOrder) throws OrderException, CustomerException, SubTasksException {
        if (dtoOrder.getUsername() == null || dtoOrder.getPassword() == null ||
                dtoOrder.getDescription() == null || dtoOrder.getSubtaskName() == null ||
                dtoOrder.getProposedPrice() == null || dtoOrder.getAddress() == null)
            throw new OrderException("you should fill all of the items");
        Customer customer = customerService.findByUsernameAndPassword(dtoOrder.getUsername(), dtoOrder.getPassword());
        SubTasks subtask = subTaskServices.findByName(dtoOrder.getSubtaskName());
        if (dtoOrder.getProposedPrice() < subtask.getBasePrice())
            throw new OrderException("you can not send dtoOrder price less than base price edit it and try again");
        if (!Validation.validDate(dtoOrder.getFinishDate()))
            throw new OrderException("wrong date you should fill it this way '1401-01-01' and date can not be before today");
        Orders order = ProductMapper.INSTANCE.orderSaveToOrder(dtoOrder);
        Opinion opinion = Opinion.builder().satisfaction(0).opinion("").build();
        order.setJobStatus(JobStatus.WAITING_FOR_SUGGESTION);
        order.setSubTasks(subtask);
        order.setCustomer(customer);
        order.setOpinion(opinion);
        orderRepository.save(order);
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

    //section show jobs (Expert)
    @Override
    public List<OrderShow> jobforExpert(OrderSave order) throws OrderException, ExpertException, SubTasksException {
        if (order.getUsername() == null || order.getPassword() == null || order.getSubtaskName() == null)
            throw new OrderException("you should fill all of the items");
        final Expert expert = expertService.findByUsernameAndPassword(order.getUsername(), order.getPassword());
        final SubTasks subtask = subTaskServices.findByName(order.getSubtaskName());
        final List<Orders> listOrders = orderRepository.findByJobStatusAndSubTasks(JobStatus.WAITING_FOR_SUGGESTION, subtask);
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
                    .proposedPrice(orders.getProposedPrice())
                    .description(orders.getDescription())
                    .finishDate(orders.getFinishDate())
                    .id(orders.getId())
                    .build();
            result.add(orderShow);
        });
        return result;
    }

    //section send Offer
    @Override
    public OrderShow sendOffer(OffersSave offer) throws OrderException, ExpertException, SubTasksException {
        // چک کردن که اطلاعات خالی نباشن
        if (offer.getUsername() == null || offer.getPassword() == null || offer.getOrderId() == null)
            throw new OrderException("you should fill all of the items");
        // پیدا کردن متخصص
        Expert expert = expertService.findByUsernameAndPassword(offer.getUsername(), offer.getPassword());
        // پیدا کردن سفارش
        final Optional<Orders> byId = orderRepository.findById(offer.getOrderId());
        if (byId.isEmpty())
            throw new OrderException("wrong order id");
        if (byId.get().getJobStatus() != JobStatus.WAITING_FOR_SUGGESTION)
            throw new OrderException("you can not send offer for this order because " +
                    "the status is not waiting for expert");
        final Orders order = byId.get();

        OrderSave orderSave = OrderSave.builder()
                .username(offer.getUsername()).password(offer.getPassword())
                .subtaskName(offer.getSubtaskName()).build();
        final List<OrderShow> orderShows = jobforExpert(orderSave);
        for (int i = 0; i < orderShows.size(); i++) {
            if (Objects.equals(orderShows.get(i).getId(), offer.getOrderId())) {
                final Orders orders = orderRepository.findById(offer.getOrderId()).get();
                if (orders.getJobStatus() != JobStatus.WAITING_FOR_SUGGESTION)
                    throw new OrderException("you can not send offer for this order because " +
                            "the status is not waiting for expert");
                Offers offers = Offers.builder()
                        .suggestion(offer.getSuggestion()).date(offer.getDate())
                        .periodOfTime(offer.getPeriodOfTime()).expert(expert).orders(order)
                        .build();
                offerService.save(offers);
                return orderShows.get(i);
            }
        }
        throw new OrderException("failed to do");
    }

    @Override
    public Optional<Orders> findById(Long offerId) throws OrderException {
        final Optional<Orders> byId = orderRepository.findById(offerId);
        if (byId.isEmpty())
            throw new OrderException("wrong id");
        return byId;
    }

    //section get all suggestion
    @Override
    public List<OffersSet> getAllExpertSuggestions(OffersGet offer) throws
            OfferException, CustomerException, OrderException {
        // check offer is not null
        if (offer.getUsername() == null || offer.getPassword() == null || offer.getOrderId() == null)
            throw new OfferException("you should fill all of the items ");
        // find customer
        final Customer customer = customerService.findByUsernameAndPassword(offer.getUsername(), offer.getPassword());
        // find order
        final Optional<Orders> order = orderRepository.findById(offer.getOrderId());
        if (order.isEmpty())
            throw new OrderException("there is no result");
        if (!Objects.equals(order.get().getCustomer().getId(), customer.getId()))
            throw new OrderException("sorry you cant see this result");
        // find offer
        final List<Offers> offers = offerService.findByOrderId(offer.getOrderId());
        if (offers == null)
            throw new OrderException("there is no order");
        final List<OffersSet> result = ProductMapper.INSTANCE.offerToDtoList(offers);
        return result;
    }

    // section select suggastion
    @Override
    public OrderShow confirmOrder(Long idOrder, Long idOffer) throws OrderException, OfferException {
        if (idOrder == null || idOffer == null)
            throw new OrderException("you should fill all of the items");
        final Optional<Orders> byId = orderRepository.findById(idOrder);
        if (byId.isEmpty())
            throw new OrderException("order not found");
        final Orders order = byId.get();
        final Offers offer = offerService.findById(idOffer);
        order.setJobStatus(JobStatus.EXPERT_ON_WAY);
        offer.setStatus(true);
        final OrderShow result = ProductMapper.INSTANCE.orderTodto(order);
        result.setProposedPrice(offer.getSuggestion());
        result.setFinishDate(offer.getDate());
        offerService.saveOffers(offer);
        orderRepository.save(order);
        return result;
    }

    //section start work
    @Override
    public OrderShow startWork(Long id) throws OrderException {
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        final Orders order = byId.get();
        if (order.getJobStatus() != JobStatus.EXPERT_ON_WAY)
            throw new OrderException("the order is not in expert on way situation");
        order.setJobStatus(JobStatus.STARTED);
        orderRepository.save(order);
        return ProductMapper.INSTANCE.orderTodto(order);
    }

    //section job finished
    @Override
    public OrderShow doneJob(Long id) throws OrderException, OfferException {
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("failed to find order");
        final Orders order = byId.get();
        if (order.getJobStatus() != JobStatus.STARTED)
            throw new OrderException("the order should be started first");
        Offers offer = offerService.findByOrderAndStatus(id);
        Time time = Time.builder().hour(offer.getPeriodOfTime().getHour()).minute(offer.getPeriodOfTime().getMinute()).build();
        final int delay = Validation.compareDate(offer.getDate(), time);
        order.setJobStatus(JobStatus.FINISHED);
        Expert expert = offer.getExpert();
        expert.setScore(expert.getScore() - (delay / 60));
        if (expert.getScore() < 0 )
            expert.setStatus(false);
        orderRepository.save(order);
        expertService.save(expert);
        return ProductMapper.INSTANCE.orderTodto(order);
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }


    //section show orders
    @Override
    public List<OrderShow> showOrders(Customer customer) throws CustomerException, OrderException {
        final Customer findCustomer = customerService.findByUsernameAndPassword(customer.getUsername(), customer.getPassword());
        final List<Orders> listOrders = orderRepository.findByCustomer(findCustomer);
        if (listOrders.size() < 1)
            throw new OrderException("the is no Order");
        return ProductMapper.INSTANCE.listOrdersToDto(listOrders);
    }

    //section pay
    @Override
    public OrderShow payWithWallet(Long id,Customer account) throws OrderException, CustomerException, OfferException, ExpertException {
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        if (byId.get().getJobStatus() != JobStatus.FINISHED)
            throw new OrderException("first finish job and try again");
        Orders order = byId.get();
        Customer customer = customerService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        Offers offer = offerService.findByOrderAndStatus(id);
        Long idExpert = offer.getExpert().getId();
        Expert expert = expertService.findById(idExpert);
        if (customer.getInventory() < offer.getSuggestion())
            throw new CustomerException("you don't have enough money");
        Long priceCustomer = customer.getInventory() - offer.getSuggestion();
        Long priceExpert = expert.getInventory() + offer.getSuggestion();
        customer.setInventory(priceCustomer);
        expert.setInventory(priceExpert);

        customerService.save(customer);
        order.setJobStatus(JobStatus.PAYED);
        orderRepository.save(order);
        expertService.save(expert);
        return ProductMapper.INSTANCE.orderTodto(byId.get());
    }

    //section opinion
    @Override
    public OrderShow sendOpinion(Long id, DtoOpinion dtoOpinion) throws OrderException {
        if (dtoOpinion.getOpinion() == null)
            throw new OrderException("fill opinion and try again");
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        Orders order = byId.get();
        if (order.getJobStatus() != JobStatus.PAYED)
            throw new OrderException("you should pay it first");
        final Opinion opinion = ProductMapper.INSTANCE.dtoToOpinion(dtoOpinion);
        order.setOpinion(opinion);
        orderRepository.save(order);
        return ProductMapper.INSTANCE.orderTodto(order);
    }

}
