package com.example.springboot.dto.offer;

import com.example.springboot.entity.Expert;
import com.example.springboot.entity.Time;
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
    String date;
    Time periodOfTime;
    Expert expert;
}
