package com.example.springboot.dto.subTask;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    Long basePrice;
}
