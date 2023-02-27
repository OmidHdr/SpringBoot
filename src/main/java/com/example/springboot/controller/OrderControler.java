package com.example.springboot.controller;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Customer;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OfferService;
import com.example.springboot.services.OrderService;
import jakarta.validation.Valid;
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

    // ارسال سفارش
    @PostMapping("/sendOrder")
    public void sendOrder(@RequestBody OrderSave order) throws CustomerException, SubTasksException, OrderException {
        orderService.saveOrder(order);
    }

    // مشاهده سفارش های ارسال شده
    @PostMapping("/showOrders")
    public List<OrderShow> showOrders(@RequestBody Customer customer) throws CustomerException, OrderException {
        return orderService.showOrders(customer);
    }

    // ارسال پیشنهاد توسط متخصص
    @PostMapping("/sendOfferFromExpert")
    public OrderShow sendOffer(@RequestBody OffersSave offer) throws ExpertException, SubTasksException, OrderException {
        return orderService.sendOffer(offer);
    }

    // مشاهده سفارش ها توسط متخصص
    @PostMapping("/jobforExpert")
    public List<OrderShow> jobForExpert(@RequestBody OrderSave order) throws ExpertException, SubTasksException, OrderException {
        return orderService.jobforExpert(order);
    }

    // مشاهده لیست پشنهادات ارسال شده
    @PostMapping("/showAllExpertSuggestions")
    public List<OffersSet> getAllExpertSuggestions(@RequestBody OffersGet offer) throws CustomerException, OfferException, OrderException, SubTasksException {
        return orderService.getAllExpertSuggestions(offer);
    }

    // انتخاب متخصص برای سفارش توسط مشتری
    @PostMapping("/selectSuggastion/{order}/{offer}")
    public OrderShow selectExpertForOrder(@PathVariable(value = "order") Long idOrder
            , @PathVariable(value = "offer") Long idOffer) throws OrderException, OfferException {
        return orderService.confirmOrder(idOrder, idOffer);
    }

    // با گرفتن آی دی offer
    // شروع شدن کار
    @PostMapping("/startJob/{id}")
    public OrderShow startWork(@PathVariable(value = "id") Long id) throws OrderException {
        return orderService.startWork(id);
    }


    @PostMapping("/doneJob/{id}")
    public OrderShow doneJob(@PathVariable(value = "id") Long id) throws OrderException, OfferException {
        return orderService.doneJob(id);
    }

    @PostMapping("/opinion/{id}")
    public OrderShow sendOpinion(@Valid @PathVariable(value = "id") Long id, @RequestBody DtoOpinion dtoOpinion) throws OrderException, OfferException {
        return orderService.sendOpinion(id, dtoOpinion);
    }

}
