package com.example.springboot.entity;

import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class OrderTest {

    private final OrderService orderService;
    private final Order order;

    OrderTest(OrderService orderService, Order order) {
        this.orderService = orderService;
        this.order = order;
    }

    @Test
    void saveOrder(){
        Customer customer = (Customer) Customer.builder().firstName("omid").lastName("heidary").email("omid@gmail.com").username("omidalpha").password("Aa@123456").userRole(UserRole.CUSTOMER).build();
        Offers offers = Offers.builder().customerSuggestion(100000L).build();
        order.setCustomer(customer);
        order.setDescription("painting room");
        order.setProposedPrice(20000L);
        order.setStartDate("1402-01-01");
        order.setAddress("molavi");
        order.setJobStatus(JobStatus.WAITING_FOR_SEGGESTION);
        order.setOffer(offers);
//        order.setServices();
//        order.setSubServices();
        orderService.saveOrder(order);
    }

}