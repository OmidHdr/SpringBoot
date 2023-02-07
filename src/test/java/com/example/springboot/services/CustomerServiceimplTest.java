package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceimplTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceimpl customerService;

    @Test
    @Order(1)
    void saveCustomer() throws CustomerException {
        Customer customer = Customer.builder()
                .firstName("omid")
                .lastName("heidary")
                .email("omid@gmail.com")
                .username("omidalfa")
                .password("Aa@123456")
                .userRole(UserRole.CUSTOMER)
                .build();
        BDDMockito.given(customerRepository.save(customer)).willReturn(customer);
        Customer customer1 = customerService.saveCustomer(customer);
        assertNotNull(customer1);
    }

    @Test
    @Order(2)
    void findByUsernameAndPassword() {
        Customer customer = Customer.builder().username("omidalfa").password("Aa@123456").build();
        BDDMockito.given(customerRepository.findByUsernameAndPassword("omidalfa","Aa@123456")).willReturn(customer);
        Customer user = customerService.findByUsernameAndPassword("omidalfa", "Aa@123456");
        assertNotNull(user);
        assertNotNull("omid@gmail.com",user.getEmail());
    }

    
}