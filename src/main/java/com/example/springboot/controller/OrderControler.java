package com.example.springboot.controller;

import com.example.springboot.dto.*;
import com.example.springboot.entity.Offers;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OfferService;
import com.example.springboot.services.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderControler {

    private final OrderService orderService;
    private final OfferService offerService;

    public OrderControler(OrderService orderService, OfferService offerService) {
        this.orderService = orderService;
        this.offerService = offerService;
    }

    @PostMapping("/sendOrder")
    public void sendOrder(@RequestBody SaveOrder order) throws CustomerException, SubTasksException, OrderException {
        orderService.saveOrder(order);
    }
    //مشاهدده لیست پینهادات
    @PostMapping("/jobforExpert")
    public List<ShowOrder> jobForExpert(@RequestBody SaveOrder order) throws ExpertException, SubTasksException, OrderException {
        return orderService.jobforExpert(order);
    }
    // ارسال پیشنهاد توسط متخصص
    @PostMapping("/sendOfferFromExpert")
    public ShowOrder confirmJob(@RequestBody SaveOffer offer) throws ExpertException, SubTasksException, OrderException {
        return orderService.confirmJob(offer);
    }
    // مشاهده لیست پشنهادات ارسال شده توسط متخصص ها
    @PostMapping("/showExpertSuggestions")
    public List<SetOffer> getAllExpertSuggestions (@RequestBody GetOffers offer) throws CustomerException, OfferException, OrderException, SubTasksException {
        return orderService.getAllExpertSuggestions(offer);
    }
    // انتخاب متخصص برای سفارش توسط مشتری
}
