package com.example.springboot.services;

import com.example.springboot.dto.GetOffers;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceimpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceimpl(OfferRepository offerRepository ){
        this.offerRepository = offerRepository;
    }

    @Override
    public void saveOffers(Offers offers) {
        offerRepository.save(offers);
    }

    @Override
    public List<Offers> findByOrders(Long id) {
        return offerRepository.findByOrders(id);
    }

}