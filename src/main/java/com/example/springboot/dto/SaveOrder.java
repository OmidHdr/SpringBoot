package com.example.springboot.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveOrder {
    String username;
    String password;
    String subtaskName;
    String description;
    String address;
    Long priceSuggestion;
}
