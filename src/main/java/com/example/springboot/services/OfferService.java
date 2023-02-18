package com.example.springboot.services;

import com.example.springboot.entity.Offers;
import com.example.springboot.exeption.OfferException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferService {
    void saveOffers(Offers offers);

    List<Offers> findByOrders(Long orders);

    Offers findById(Long id) throws OfferException;

}
