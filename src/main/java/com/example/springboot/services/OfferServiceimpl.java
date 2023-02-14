package com.example.springboot.services;

import com.example.springboot.entity.Offers;
import com.example.springboot.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceimpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceimpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void saveOffers(Offers offers) {
        offerRepository.save(offers);
    }
}