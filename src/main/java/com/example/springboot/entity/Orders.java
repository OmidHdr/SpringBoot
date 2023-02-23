package com.example.springboot.entity;

import com.example.springboot.entity.Enum.JobStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    Customer customer;

    String description;

    @Column(name = "proposed_price")
    Long proposedPrice;

    @Column(name = "finish_date")
    String finishDate;
    String address;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    JobStatus jobStatus;

    @OneToMany(mappedBy = "orders",fetch = FetchType.LAZY)
    List<Offers> offer;

    @Embedded
    Opinion opinion;

    @ManyToOne(fetch = FetchType.LAZY)
    SubTasks subTasks;

}