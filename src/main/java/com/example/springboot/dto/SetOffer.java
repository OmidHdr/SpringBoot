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
public class SetOffer {
    Long id;
    String date;
    String periodOfTime;
    Long suggestion;
    SaveExpert expert;
}
