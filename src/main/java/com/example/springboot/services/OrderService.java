package com.example.springboot.services;

import com.example.springboot.dto.expert.ExpertSet;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(OrderSave orders) throws OrderException, CustomerException, SubTasksException;
    List<OrderShow> jobforExpert(OrderSave order) throws OrderException, ExpertException, SubTasksException;

    OrderShow confirmJob(OffersSave offer) throws OrderException, ExpertException, SubTasksException;

    Optional<Orders> findById(Long offerId) throws OrderException;

    //section get all suggestion
    List<OffersSet> getAllExpertSuggestions(OffersGet offer) throws OfferException, OrderException, CustomerException, SubTasksException;

    OrderShow confirmOrder(Long idOrder , Long idOffer) throws OrderException, OfferException;

    OrderShow startWork(Long id) throws OrderException;

    OrderShow doneJob(Long id, ExpertSet expertSet) throws OrderException, ExpertException;
    Orders save(Orders orders);
}
