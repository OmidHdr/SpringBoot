package com.example.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchExpert {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Boolean status;
    private int scoreMin;
    private int scoreMax;
    private String subTaskName;


}
