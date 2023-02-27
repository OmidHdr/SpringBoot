package com.example.springboot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveExpertFromSubService {
    @NotNull
    String username;
    @NotNull
    String subtaskName;
}
