package com.example.springboot.services;

import com.example.springboot.dto.ChangePassword;
import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.validation.Validation;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (account.getPassword() == null || !Validation.validPassword(account.getPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Validation.validateEmail(account.getEmail()))
            throw new CustomerException("Email Not valid");
        account.setInventory(0L);
        account.setDate(LocalDate.now());
        account.setUserRole(UserRole.ROLE_CUSTOMER);
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
        final Customer customer = customerRepository.findByUsernameAndPassword(username, passwordEncoder.encode(password));
        if (customer == null)
            throw new CustomerException("wrong username or password");
        return customer;
    }

    //section change password
    @Override
    public Customer changePassword(Customer customer, String password) {
        customer.setPassword(passwordEncoder.encode(password));
        return customerRepository.save(customer);
    }

    //section save
    @Override
    public Customer save(Customer account) {
        return customerRepository.save(account);
    }

    //section find
    @Override
    public Customer findCustomer(String find, String item) throws CustomerException {
        final List<Customer> byitem = customerRepository.findByItem(find, item);
        return new Customer();
    }

    //section find by username
//    public Customer findByUsername(String username) throws CustomerException {
//        final Optional<Customer> byId = customerRepository.findByUsername(username);
//        if (byId.isEmpty())
//            throw new CustomerException("User not found");
//        Customer customer = byId.get();
//        return new org.springframework.security.core.userdetails.User(
//                customer.getUsername(), customer.getPassword(), getAuthorities(customer.getUserRole()));
//        return new org.springframework.core.userdetails.Customer(customer.getUsername(),customer.getPassword()
//        ,customer.isEnabled(),true,true,true,getAuthorities);
//    }

    private GrantedAuthority getAuthorities(UserRole roles) {
        return new SimpleGrantedAuthority(roles.name());
    }
}
