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
public class SaveOffer {
    Long offerId;
    String username;
    String password;
    String subtaskName;
    Long suggestion;
    String timeStart;
    String periodOfTime;
}
