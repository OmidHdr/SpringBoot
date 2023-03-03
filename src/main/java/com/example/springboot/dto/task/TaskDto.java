package com.example.springboot.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskDto {
    @NotNull
    private String name;
}
