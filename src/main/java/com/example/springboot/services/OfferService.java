package com.example.springboot.services;

import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Offers;
import com.example.springboot.exeption.OfferException;

import java.util.List;

public interface OfferService {
    void saveOffers(Offers offers);

    List<Offers> findByOrderId(Long orders);

    Offers findById(Long id) throws OfferException;

    Offers findByOrderAndStatus(Long id) throws OfferException;

    Offers save(Offers offer);

}
