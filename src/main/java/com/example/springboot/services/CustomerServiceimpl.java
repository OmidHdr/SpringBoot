package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceimpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceimpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //section save Customer
    @Override
    public Customer saveCustomer(Customer account) throws CustomerException {
        final UserRole userRole = account.getUserRole();
        Validation validation = new Validation();
        if (!validation.validString(account.getFirstName())&& validation.validString(account.getLastName()))
            throw new CustomerException("wrong firstname or lastname !!");
        if (!userRole.equals(UserRole.CUSTOMER))
            throw new CustomerException("Wrong role !!");
        if (!validation.validPassword(account.getPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (!validation.validateEmail(account.getEmail()))
            throw new CustomerException("Email Not valid");
        account.setInventory(0);
        return customerRepository.save(account);
    }

    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        return customerRepository.findByUsernameAndPassword(username, password);
    }

}
