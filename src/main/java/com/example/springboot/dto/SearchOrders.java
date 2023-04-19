package com.example.springboot.dto;

import com.example.springboot.entity.Enum.JobStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchOrders {

    private String address;

    private String description;

    private String finishDate;

    private String jobStatus;

    private Long proposedPrice;

    private String subTaskName;

    private String customerUsername;
    
}
