package com.cars.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String model;
    private int manufactureYear;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id") // FK
    private Brand brand;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "car_carOwner")
    private List<CarOwner> carOwners;
}