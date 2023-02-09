package com.example.springboot.entity;

import com.example.springboot.entity.Enum.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;

@NoArgsConstructor
@Entity
@SuperBuilder
public class Customer extends Account{

}
