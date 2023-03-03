package com.example.springboot.dto.expert;


import com.example.springboot.entity.SubTasks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class dtoExpert {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<SubTasks> subTasks;
    private String username;
}
