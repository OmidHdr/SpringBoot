package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceimplTest {

    @Autowired
    private CustomerServiceimpl customerService;

    //section register
    @Test
    @DisplayName("Register")
    void saveCustomer() throws CustomerException {
        Customer customer = Customer.builder()
                .firstName("omid")
                .lastName("heidary")
                .email("omid@gmail.com")
                .username("omidalfa")
                .password("Aa@123456")
                .userRole(UserRole.CUSTOMER)
                .build();
        Customer customer1 = customerService.saveCustomer(customer);
        assertNotNull(customer1);
    }

    //section login
    @Test
    @DisplayName("login")
    void findByUsernameAndPassword() throws CustomerException {
        Customer user = customerService.findByUsernameAndPassword("omidalfa", "Aa@123456");
        assertNotNull(user);
    }
    //section change password
    @Test
    void changePassword() throws CustomerException {
        Customer customer = Customer.builder().username("omidalpha").password("Aa123456$").build();
        final Customer newCustomer = customerService.changePassword(customer.getUsername(),customer.getPassword(), "Aa123456@");
        assertThat(newCustomer.getPassword()).isEqualTo("Aa123456@");
    }

}