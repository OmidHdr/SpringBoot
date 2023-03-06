package com.example.springboot.dto.subTask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubTaskSave {

    private String name;
    private String taskName;
    private String description;
    private Long basePrice;
}
