package com.example.springboot.dto.order;

import com.example.springboot.entity.Enum.JobStatus;
import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoOrder {

    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    private String description;

    private Long proposedPrice;

    private String address;

    private String finishDate;

}
