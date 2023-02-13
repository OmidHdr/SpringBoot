package com.example.springboot.entity;

import com.example.springboot.entity.Enum.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    Customer customer;

    String description;

    @Column(name = "proposed_price")
    Long proposedPrice;

    @Column(name = "start_date")
    String startDate;
    String address;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    JobStatus jobStatus;

    @OneToOne
    Offers offer;

    String opinion;

    @ManyToMany
    Set<Tasks> tasks;

    @ManyToMany
    Set<SubTasks> subTasks;

}