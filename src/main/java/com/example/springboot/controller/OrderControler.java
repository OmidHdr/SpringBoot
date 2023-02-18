package com.example.springboot.controller;

import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderConfirm;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Orders;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OfferService;
import com.example.springboot.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public void sendOrder(@RequestBody OrderSave order) throws CustomerException, SubTasksException, OrderException {
        orderService.saveOrder(order);
    }
    //مشاهدده لیست پینهادات
    @PostMapping("/jobforExpert")
    public List<OrderShow> jobForExpert(@RequestBody OrderSave order) throws ExpertException, SubTasksException, OrderException {
        return orderService.jobforExpert(order);
    }
    // ارسال پیشنهاد توسط متخصص
    @PostMapping("/sendOfferFromExpert")
    public OrderShow confirmJob(@RequestBody OffersSave offer) throws ExpertException, SubTasksException, OrderException {
        return orderService.confirmJob(offer);
    }
    // مشاهده لیست پشنهادات ارسال شده توسط متخصص ها
    @PostMapping("/showExpertSuggestions")
    public List<OffersSet> getAllExpertSuggestions (@RequestBody OffersGet offer) throws CustomerException, OfferException, OrderException, SubTasksException {
        return orderService.getAllExpertSuggestions(offer);
    }
    // انتخاب متخصص برای سفارش توسط مشتری
    @PostMapping("/selectExperyById/{order}/{offer}")
    public OrderShow selectExpertForOrder(@PathVariable(value = "order")Long idOrder
            ,@PathVariable(value = "offer") Long idOffer) throws OrderException, OfferException {
        return orderService.confirmOrder(idOrder , idOffer);
    }
}
