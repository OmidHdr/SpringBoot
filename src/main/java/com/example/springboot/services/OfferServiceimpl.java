package com.example.springboot.services;

import com.example.springboot.entity.Offers;
import com.example.springboot.exeption.OfferException;
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
        return offerRepository.findByOrderId(id);
    }

    @Override
    public Offers findById(Long id) throws OfferException {
        final Offers offers = offerRepository.findById(id).get();
        if (offers == null)
            throw new OfferException("wrong offer id ");
        return offers;
    }

}