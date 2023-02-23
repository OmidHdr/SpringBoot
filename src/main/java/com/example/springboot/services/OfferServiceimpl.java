package com.example.springboot.services;

import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.OfferException;
import com.example.springboot.exeption.OrderException;
import com.example.springboot.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceimpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceimpl(OfferRepository offerRepository){
        this.offerRepository = offerRepository;
    }

    @Override
    public void saveOffers(Offers offers) {
        offerRepository.save(offers);
    }

    @Override
    public List<Offers> findByOrderId(Long id) {
        return offerRepository.findByOrderId(id);
    }

    //section find by id
    @Override
    public Offers findById(Long id) throws OfferException {
        final Optional<Offers> byId = offerRepository.findById(id);
        if (byId.isEmpty())
            throw new OfferException("wrong offer id ");
        return byId.get();
    }

    //section find by order and status
    @Override
    public Offers findByOrderAndStatus(Long id) throws OfferException {
        final Offers result = offerRepository.findByOrderAndStatus(id);
        if (result == null)
            throw new OfferException("there is no offer");
        return result;
    }

    @Override
    public Offers save(Offers offer) {
        return offerRepository.save(offer);
    }


}