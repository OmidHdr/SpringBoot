package com.example.springboot.services;

import com.example.springboot.entity.Offers;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferService {
    void saveOffers(Offers offers);

    @Query(value = "from Offers",nativeQuery = true)
//    @Query(value = "select id , date ,suggestion , period_of_time , expert_id from offers where orders_id =1;",)
    List<Offers> findByOrders(Long orders);


}
