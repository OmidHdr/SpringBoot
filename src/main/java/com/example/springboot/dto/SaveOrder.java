package com.example.springboot.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveOrder {
    String username;
    String password;
    String subtaskName;
    String description;
    String address;
    String startDate;
    Long priceSuggestion;
}
