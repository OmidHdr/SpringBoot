package com.example.springboot.dto.order;

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
public class OrderSave {
    @NotNull
    String subtaskName;
    @NotNull
    String description;
    @NotNull
    String address;
    @NotNull
    String finishDate;
    @NotNull
    Long proposedPrice;
}
