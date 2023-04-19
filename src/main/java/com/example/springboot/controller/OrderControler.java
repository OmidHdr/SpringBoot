package com.example.springboot.controller;

import com.example.springboot.dto.DtoOpinion;
import com.example.springboot.dto.offer.OffersGet;
import com.example.springboot.dto.offer.OffersSave;
import com.example.springboot.dto.offer.OffersSet;
import com.example.springboot.dto.order.OrderSave;
import com.example.springboot.dto.order.OrderShow;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Expert;
import com.example.springboot.exeption.*;
import com.example.springboot.services.OfferService;
import com.example.springboot.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderControler {

    private final OrderService orderService;
    private final OfferService offerService;

    public OrderControler(OrderService orderService, OfferService offerService) {
        this.orderService = orderService;
        this.offerService = offerService;
    }

    //section save order
    // ارسال سفارش
    @PostMapping("/send")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String sendOrder(@RequestBody OrderSave order) throws CustomerException, SubTasksException, OrderException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.saveOrder(order,customer);
        return "Order saved successfully";
    }

    //section show order
    // مشاهده سفارش های ارسال شده
    @GetMapping("/showAll")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderShow> showOrders() throws CustomerException, OrderException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.showOrders(customer);
    }

    //section find job
    // مشاهده سفارش ها توسط متخصص
    @PostMapping("/jobforExpert")
    @PreAuthorize("hasRole('EXPERT')")
    public List<OrderShow> jobForExpert(@RequestBody OrderSave order) throws ExpertException, SubTasksException, OrderException {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.jobforExpert(order,expert);
    }
    //section send offer
    // ارسال پیشنهاد توسط متخصص
    @PostMapping("/sendOfferFromExpert")
    @PreAuthorize("hasRole('EXPERT')")
    public OrderShow sendOffer(@RequestBody OffersSave offer) throws ExpertException, SubTasksException, OrderException {
        Expert expert = (Expert) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.sendOffer(offer,expert);
    }
    

    //section get all expert
    // مشاهده لیست پشنهادات ارسال شده
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/showAllExpertSuggestions/{id}")
    public List<OffersSet> getAllExpertSuggestions(@PathVariable Long id) throws CustomerException, OfferException, OrderException, SubTasksException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.getAllExpertSuggestions(id,customer);
    }

    //section select expert
    // انتخاب متخصص برای سفارش توسط مشتری
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/selectSuggastion/{order}/{offer}")
    public OrderShow selectExpertForOrder(@PathVariable(value = "order") Long idOrder
            , @PathVariable(value = "offer") Long idOffer) throws OrderException, OfferException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.confirmOrder(customer,idOrder, idOffer);
    }

    //section start
    // با گرفتن آی دی offer
    // شروع شدن کار
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/startJob/{id}")
    public OrderShow startWork(@PathVariable Long id) throws OrderException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.startWork(id,customer);
    }

    // section done
    // پایان کار
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/doneJob/{id}")
    public OrderShow doneJob(@PathVariable Long id) throws OrderException, OfferException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.doneJob(id,customer);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/opinion/{id}")
    public OrderShow sendOpinion(@Valid @PathVariable Long id, @RequestBody DtoOpinion dtoOpinion) throws OrderException, OfferException {
        Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.sendOpinion(id, dtoOpinion,customer);
    }

}
