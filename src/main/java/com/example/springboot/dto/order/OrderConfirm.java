package com.example.springboot.dto.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class OrderConfirm {
    Long id;
    
}
