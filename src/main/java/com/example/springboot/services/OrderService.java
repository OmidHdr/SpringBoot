package com.example.springboot.services;

import com.example.springboot.dto.*;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void saveOrder(SaveOrder orders) throws OrderException, CustomerException, SubTasksException;
    List<ShowOrder> jobforExpert(SaveOrder order) throws OrderException, ExpertException, SubTasksException;

    ShowOrder confirmJob(SaveOffer offer) throws OrderException, ExpertException, SubTasksException;

    Optional<Orders> findById(Long offerId) throws OrderException;

    //section get all suggestion
    List<SetOffer> getAllExpertSuggestions(GetOffers offer) throws OfferException, OrderException, CustomerException, SubTasksException;
}
