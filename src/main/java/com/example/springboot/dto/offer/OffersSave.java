package com.example.springboot.dto.offer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OffersSave {
    Long orderId;
    String username;
    String password;
    String subtaskName;
    Long suggestion;
    String timeStart;
    String periodOfTime;
}
