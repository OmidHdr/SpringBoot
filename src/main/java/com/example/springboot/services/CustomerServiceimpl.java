package com.example.springboot.services;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceimpl implements CustomerService {

    @Autowired
    private OrderService orderService;
    private final CustomerRepository customerRepository;
    private final OfferService offerService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceimpl(CustomerRepository customerRepository, OfferService offerService, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.offerService = offerService;
        this.passwordEncoder = passwordEncoder;
    }

    //section register
    @Override
    public Customer saveCustomer(Customer account) throws CustomerException {
        final UserRole userRole = account.getUserRole();
        if (account.getFirstName() == null || account.getLastName() == null || !Validation.validString(account.getFirstName()) || !Validation.validString(account.getLastName()))
            throw new CustomerException("wrong firstname or lastname !!");
        if (account.getUserRole() == null || !userRole.equals(UserRole.ROLE_CUSTOMER))
            throw new CustomerException("Wrong Role !!");
        if (account.getPassword() == null || !Validation.validPassword(account.getPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(account.getEmail()))
            throw new CustomerException("Email Not valid");
        account.setInventory(0L);
        account.setDate(LocalDate.now());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
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
        if (customer == null)
            throw new CustomerException("wrong username or password");
        return customer;
    }

    //section change password
    @Override
    public Customer changePassword(ChangePassword changePassword) throws CustomerException {
        if (changePassword.getPassword() == null || changePassword.getUsername() == null || changePassword.getNewPassword() == null)
            throw new CustomerException("you should fill all of the items");
        Customer byUsername = findByUsernameAndPassword(changePassword.getUsername(), changePassword.getPassword());
        if (!Validation.validPassword(changePassword.getNewPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        byUsername.setPassword(changePassword.getNewPassword());
        return customerRepository.save(byUsername);
    }

    //section save
    @Override
    public Customer save(Customer account){
        return customerRepository.save(account);
    }

    //section find
    @Override
    public Customer findCustomer(String find ,String item) throws CustomerException {
        final List<Customer> byitem = customerRepository.findByItem(find,item);
        return new Customer();
    }


}
