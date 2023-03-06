package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceimpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSender emailSender;


    //section register
    @Override
    public Customer saveCustomer(Customer account) throws CustomerException {
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
        // send email
        emailSender.sendEmail(account.getEmail(),"confirm your email","click on this link to confirm");
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
    public Customer findCustomer(String find, String item) {
        final List<Customer> byitem = customerRepository.findByItem(find, item);
        return new Customer();
    }

}
