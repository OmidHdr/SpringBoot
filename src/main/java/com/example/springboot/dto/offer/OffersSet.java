package com.example.springboot.dto.offer;

import com.example.springboot.dto.expert.ExpertGet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OffersSet {
    Long id;
    String date;
    String periodOfTime;
    Long suggestion;
    ExpertGet expert;
}
