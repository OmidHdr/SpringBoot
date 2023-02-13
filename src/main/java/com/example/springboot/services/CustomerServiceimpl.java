package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.validation.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerServiceimpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceimpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //section register
    @Override
    public Customer saveCustomer(Customer account) throws CustomerException {
        final UserRole userRole = account.getUserRole();
        if (account.getFirstName() == null || account.getLastName() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new CustomerException("wrong firstname or lastname !!");
        if (account.getUserRole() == null || !userRole.equals(UserRole.CUSTOMER))
            throw new CustomerException("Wrong Role !!");
        if (account.getPassword() == null || !Validation.validPassword(account.getPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(account.getEmail()))
            throw new CustomerException("Email Not valid");
        account.setInventory(0);
        account.setDate(LocalDate.now());
        try {
            return customerRepository.save(account);
        } catch (Exception e) {
            throw new CustomerException("User already exist");
        }

    }

    //section login
    @Override
    public Customer findByUsernameAndPassword(String username, String password) throws CustomerException {
        final Customer customer = customerRepository.findByUsernameAndPassword(username, password);
        if (customer==null)
            throw new CustomerException("wrong username or password");
        return customer;
    }

    //section change password
    @Override
    public Customer changePassword(String username , String password, String newPassword) throws CustomerException {
        final Customer byUsername = findByUsernameAndPassword(username,password);
        if (!Validation.validPassword(newPassword))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        byUsername.setPassword(newPassword);
        return customerRepository.save(byUsername);
    }


}
