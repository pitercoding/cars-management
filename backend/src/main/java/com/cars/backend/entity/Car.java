package com.cars.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    private int manufactureYear;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // FK
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "car_carOwner")
    private List<CarOwner> carOwners;

    @ManyToMany
    @JoinTable(
            name = "car_accessory",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private List<Accessory> accessories = new ArrayList<>();
}