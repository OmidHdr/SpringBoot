package com.example.springboot.exeption;

public class CustomerException extends Exception {
    public CustomerException(String message) {
        super(message);
    }
}
