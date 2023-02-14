package com.example.springboot.exeption;

public class OrderException extends Exception {
    public OrderException(String message) {
        super(message);
    }
}
