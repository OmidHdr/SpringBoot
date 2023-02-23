package com.example.springboot.dto.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSave {
    String username;
    String password;
    String subtaskName;

    String description;
    String address;
    String finishDate;
    Long proposedPrice;
}
