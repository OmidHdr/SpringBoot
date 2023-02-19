package com.example.springboot.dto.subTask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubtaskEdit {
    String name;
    String newName;
    String newDescription;
    Long newBasePrice;
}
