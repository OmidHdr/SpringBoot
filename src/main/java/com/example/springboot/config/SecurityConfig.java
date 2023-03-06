package com.example.springboot.config;

import com.example.springboot.repository.AdminRepository;
import com.example.springboot.repository.CustomerRepository;
import com.example.springboot.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ExpertRepository expertRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/customer/register").permitAll()
                .requestMatchers("/expert/register").permitAll()
//                .requestMatchers("/admin").permitAll()
                .requestMatchers("/home").permitAll()
                .and()
                .authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username) -> customerRepository.findByUsername(username).orElseThrow(() ->
                                new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
        auth.userDetailsService((username) -> adminRepository.findByUsername(username).orElseThrow(() ->
                                new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
        auth.userDetailsService((username) -> expertRepository.findByUsername(username).orElseThrow(() ->
                                new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }

}
