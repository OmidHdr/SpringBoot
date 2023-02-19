package com.example.springboot.dto.subTask;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubTaskDto {
    Long id;
    String name;
    String description;
    Long basePrice;
}
