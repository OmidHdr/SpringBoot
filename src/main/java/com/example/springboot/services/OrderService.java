package com.example.springboot.services;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(OrderSave orders) throws OrderException, CustomerException, SubTasksException;
    List<OrderShow> jobforExpert(OrderSave order) throws OrderException, ExpertException, SubTasksException;

    //todo move this to offer service
    OrderShow sendOffer(OffersSave offer) throws OrderException, ExpertException, SubTasksException;

    Optional<Orders> findById(Long offerId) throws OrderException;

    //section get all suggestion
    List<OffersSet> getAllExpertSuggestions(OffersGet offer) throws OfferException, OrderException, CustomerException, SubTasksException;

    OrderShow confirmOrder(Long idOrder , Long idOffer) throws OrderException, OfferException;

    OrderShow doneJob(Long id) throws OrderException, OfferException;
    Orders save(Orders orders);

    //section start work
    OrderShow startWork(Long id) throws OrderException;

    List<OrderShow> showOrders(Customer customer) throws CustomerException, OrderException;

    OrderShow payWithWallet(Long id, Customer account) throws OrderException, CustomerException, OfferException, ExpertException;

    OrderShow sendOpinion(Long id, DtoOpinion dtoOpinion) throws OrderException, OfferException;
}
