package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.entity.Orders;
import com.example.springboot.entity.SubTasks;
import com.example.springboot.entity.Tasks;
import com.example.springboot.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OrdersServiceimplTest {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderServiceimpl orderService;

//    @Test
//    void saveOrder() {
//        Customer customer = Customer.builder().firstName("omid").lastName("heidary").email("omid@gmail.com")
//                .userRole(UserRole.CUSTOMER).password("Aa@123456").username("omid123456").build();
//        Tasks tasks = Tasks.builder().name("house").build();
//        SubTasks subTasks = SubTasks.builder().task(tasks).basePrice(100000L).description("cleaning").build();
//        Orders orders = Orders.builder().customer(customer).description("clean hole house")
//                .proposedPrice(120000L).startDate("1402-01-05").address("molavi").jobStatus(JobStatus.WAITING_FOR_EXPERT)
//                .tasks(Collections.singleton(tasks)).subTasks(Collections.singleton(subTasks)).build();
//        BDDMockito.given(orderRepository.save(orders)).willReturn(orders);
//        final Orders result = orderService.saveOrder(orders);
//        assertNotNull(result);
//    }
}