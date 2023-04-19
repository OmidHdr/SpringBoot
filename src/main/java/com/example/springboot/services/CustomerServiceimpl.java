package com.example.springboot.services;

import com.example.springboot.entity.Customer;
import com.example.springboot.entity.Enum.UserRole;
import com.example.springboot.exeption.CustomerException;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.utills.Utills;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceimpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSender emailSender;


    //section register
    @Override
    public Customer saveCustomer(Customer account) throws CustomerException {
        if (account.getFirstName() == null || account.getLastName() == null || !Utills.validString(account.getFirstName()) || !Utills.validString(account.getLastName()))
            throw new CustomerException("wrong firstname or lastname !!");
        if (account.getPassword() == null || !Utills.validPassword(account.getPassword()))
            throw new CustomerException("password should have at least a capital Letter and a minimal Letter and 8 character");
        if (account.getEmail() == null || !Utills.validateEmail(account.getEmail()))
            throw new CustomerException("Email Not valid");
        account.setInventory(0L);
        account.setDate(LocalDate.now());
        account.setUserRole(UserRole.ROLE_CUSTOMER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStatus(false);
        String link = UUID.randomUUID().toString();
        account.setToken(link);

        emailSender.sendEmail(account.getEmail(),"Confirm your Account",
                "Click on this link "+"http://localhost:8080/customer/verify?token="+link+" to confirm");
        
        try {
            return customerRepository.save(account);
        } catch (Exception e) {
            throw new CustomerException("User already exist");
        }

    }

    //section login
    @Override
    public Customer findByUsername(String username) throws CustomerException {
        final Optional<Customer> byUsername = customerRepository.findByUsername(username);
        if (byUsername.isEmpty())
            throw new CustomerException("wrong username or password");
        return byUsername.get();
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


    //section verify
    @Override
    public Customer verify(String token) throws CustomerException {
        Customer customer = customerRepository.findByToken(token)
                .orElseThrow(() -> new CustomerException("wrong token"));
        customer.setStatus(true);
        return customerRepository.save(customer);
    }

}
