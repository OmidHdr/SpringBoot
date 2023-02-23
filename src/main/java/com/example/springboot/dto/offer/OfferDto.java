package com.example.springboot.dto.offer;

import com.example.springboot.entity.Time;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfferDto {
    Long id;
    String date;
    Long suggestion;
    Time periodOfTime;
    String orderName;
    String expertName;
}
