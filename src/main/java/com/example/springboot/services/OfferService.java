package com.example.springboot.services;

import com.example.springboot.dto.GetOffers;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferService {
    void saveOffers(Offers offers);

    @Query(value = "from Offers",nativeQuery = true)
//    @Query(value = "select id , date ,suggestion , period_of_time , expert_id from offers where orders_id =1;",)
    List<Offers> findByOrders(Long orders);


}
