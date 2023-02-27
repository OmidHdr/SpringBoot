package com.example.springboot.exeption;

public class PaymentException extends Exception{
    public PaymentException(String message) {
        super(message);
    }
}
