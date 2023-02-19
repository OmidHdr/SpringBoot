package com.example.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    String date ;
    Long suggestion;
    String periodOfTime;
    @ManyToOne(fetch = FetchType.LAZY)
    Expert expert;
    @ManyToOne(fetch = FetchType.LAZY)
    Orders orders;
    boolean status;
}
