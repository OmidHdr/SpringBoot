package com.example.springboot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DtoOpinion {
    @Pattern(regexp = "^[1-5]$")
    int satisfaction;
    @NotNull
    String opinion;
}
