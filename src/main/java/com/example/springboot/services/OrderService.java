package com.example.springboot.services;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(OrderSave orders, Customer customer) throws OrderException, CustomerException, SubTasksException;
    List<OrderShow> jobforExpert(OrderSave order, Expert expert) throws OrderException, ExpertException, SubTasksException;
    List<OrderShow> showOrders(Customer customer) throws CustomerException, OrderException;

    OrderShow sendOffer(OffersSave offer,Expert expert) throws OrderException, ExpertException, SubTasksException;

    Optional<Orders> findById(Long offerId) throws OrderException;

    //section get all suggestion
    List<OffersSet> getAllExpertSuggestions(Long orderId,Customer customer) throws OfferException, OrderException, CustomerException, SubTasksException;

    OrderShow confirmOrder(Customer customer,Long idOrder, Long idOffer) throws OrderException, OfferException;

    OrderShow doneJob(Long id,Customer customer) throws OrderException, OfferException;

    Orders save(Orders orders);

    //section start work
    OrderShow startWork(Long id,Customer customer) throws OrderException;


    OrderShow payWithWallet(Long id, Customer customer) throws OrderException, CustomerException, OfferException, ExpertException, AdminException;

    OrderShow sendOpinion(Long id, DtoOpinion dtoOpinion,Customer customer) throws OrderException, OfferException;
}
