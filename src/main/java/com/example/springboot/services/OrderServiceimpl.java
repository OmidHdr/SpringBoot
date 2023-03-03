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
import com.example.springboot.repository.ExpertRepository;
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
    private final AdminService adminService;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private ExpertRepository expertRepository;

    public OrderServiceimpl(OrderRepository orderRepository, CustomerService customerService, SubTaskServices subTaskServices, ExpertServiceimpl expertService, AdminService adminService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.subTaskServices = subTaskServices;
        this.expertService = expertService;
        this.adminService = adminService;
    }

    //section save Order
    @Override
    public void saveOrder(OrderSave dtoOrder,Customer customer) throws OrderException, CustomerException, SubTasksException {
        if (dtoOrder.getDescription() == null || dtoOrder.getSubtaskName() == null ||
                dtoOrder.getProposedPrice() == null || dtoOrder.getAddress() == null)
            throw new OrderException("you should fill all of the items");
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
    public List<OrderShow> jobforExpert(OrderSave order,Expert expert) throws OrderException, SubTasksException {
        if ( order.getSubtaskName() == null)
            throw new OrderException("you should give a subtask name");
        if (!expert.getStatus())
            throw new OrderException("this expert is deactivate first admin should confirm");
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
    public OrderShow sendOffer(OffersSave offer,Expert expert) throws OrderException, ExpertException, SubTasksException {
        // چک کردن که اطلاعات خالی نباشن
        if ( offer.getOrderId() == null || offer.getPeriodOfTime() == null || offer.getSuggestion() == null ||
                offer.getSubtaskName() == null || offer.getDate() == null)
            throw new OrderException("you should fill all of the items");
        // پیدا کردن سفارش
        final Optional<Orders> byId = orderRepository.findById(offer.getOrderId());
        if (byId.isEmpty())
            throw new OrderException("wrong order id");
        if (byId.get().getJobStatus() != JobStatus.WAITING_FOR_SUGGESTION)
            throw new OrderException("you can not send offer for this order because " +
                    "the status is not waiting for expert");
        if (!expert.getStatus())
                throw new ExpertException("this expert is deactivate first admin should confirm");
        
        final Orders order = byId.get();
        OrderSave orderSave = OrderSave.builder()
                .subtaskName(offer.getSubtaskName()).build();
        final List<OrderShow> orderShows = jobforExpert(orderSave,expert);
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
    public List<OffersSet> getAllExpertSuggestions(Long orderId,Customer customer) throws
            OfferException, CustomerException, OrderException {
        // check subtask name is not null
        if (orderId == null)
            throw new OfferException("you should give a orderId " );
        final List<Orders> byCustomer = orderRepository.findByCustomer(customer);
        if (byCustomer.isEmpty())
            throw new OrderException("you don't have access to see this result");
        // find offer
        final List<Offers> offers = offerService.findByOrderId(orderId);
        if (offers == null)
            throw new OrderException("there is no order");
        final List<OffersSet> result = ProductMapper.INSTANCE.offerToDtoList(offers);
        return result;
    }

    // section select suggastion
    @Override
    public OrderShow confirmOrder(Customer customer,Long idOrder, Long idOffer) throws OrderException, OfferException {
        if (idOrder == null || idOffer == null)
            throw new OrderException("you should fill all of the items");
        final Optional<Orders> byId = orderRepository.findById(idOrder);
        if (byId.isEmpty())
            throw new OrderException("order not found");
        final List<Orders> byCustomer = orderRepository.findByCustomer(customer);
        if (byCustomer.isEmpty())
            throw new OrderException("you don't have access to confirm this offer");
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
    public OrderShow startWork(Long id,Customer customer) throws OrderException {
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        final List<Orders> byCustomer = orderRepository.findByCustomer(customer);
        if (byCustomer.isEmpty())
            throw new OrderException("you don't have access to start work");
        final Orders order = byId.get();
        if (order.getJobStatus() != JobStatus.EXPERT_ON_WAY)
            throw new OrderException("the order is not in expert on way situation");
        order.setJobStatus(JobStatus.STARTED);
        orderRepository.save(order);
        return ProductMapper.INSTANCE.orderTodto(order);
    }

    //section job finished
    @Override
    public OrderShow doneJob(Long id,Customer customer) throws OrderException, OfferException {
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
    public List<OrderShow> showOrders(Customer customer) throws OrderException {
        final List<Orders> listOrders = orderRepository.findByCustomer(customer);
        if (listOrders.size() < 1)
            throw new OrderException("the is no Order");
        return ProductMapper.INSTANCE.listOrdersToDto(listOrders);
    }

    //section pay
    @Override
    public OrderShow payWithWallet(Long id,Customer customer) throws OrderException, CustomerException, OfferException, ExpertException, AdminException {
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        if (byId.get().getJobStatus() != JobStatus.FINISHED)
            throw new OrderException("first finish job and try again");
        Orders order = byId.get();
        Offers offer = offerService.findByOrderAndStatus(id);
        Long idExpert = offer.getExpert().getId();
        Expert expert = expertService.findById(idExpert);
        if (customer.getInventory() < offer.getSuggestion())
            throw new CustomerException("you don't have enough money");
        Long priceCustomer = customer.getInventory() - offer.getSuggestion();
        Long priceExpert = expert.getInventory() + ((int)(offer.getSuggestion() * 0.7 ));
        customer.setInventory(priceCustomer);
        expert.setInventory(priceExpert);

        Admin admin = adminService.getAdmin("root");
        admin.setInventory((long)(offer.getSuggestion() * 0.3));                                              //show
                customerService.save(customer);
        order.setJobStatus(JobStatus.PAYED);
        orderRepository.save(order);
        expertService.save(expert);
        adminService.saveAdmin(admin);
        return ProductMapper.INSTANCE.orderTodto(byId.get());
    }

    //section opinion
    @Override
    public OrderShow sendOpinion(Long id, DtoOpinion dtoOpinion,Customer customer) throws OrderException, OfferException {
        if (dtoOpinion.getOpinion() == null)
            throw new OrderException("fill opinion and try again");
        if (dtoOpinion.getSatisfaction() < 1 || dtoOpinion.getSatisfaction() > 5)
            throw new OrderException("Satisfaction can not be less than 1 or grater than 5");
        final Optional<Orders> byId = orderRepository.findById(id);
        if (byId.isEmpty())
            throw new OrderException("can't find order");
        Orders order = byId.get();
        if (!order.getCustomer().getId().equals(customer.getId()))
            throw new OrderException("you don't have access to send opinion for this order");
        Offers offer = offerService.findByOrderAndStatus(byId.get().getId());
        Expert expert = offer.getExpert();
        if (order.getJobStatus() != JobStatus.PAYED)
            throw new OrderException("Job status is not waiting for pay");
        Opinion opinion = ProductMapper.INSTANCE.dtoToOpinion(dtoOpinion);
        expert.setScore(expert.getScore() + dtoOpinion.getSatisfaction());
        order.setJobStatus(JobStatus.CLOSE);
        order.setOpinion(opinion);
        orderRepository.save(order);
        expertService.save(expert);
        return ProductMapper.INSTANCE.orderTodto(order);
    }

}
