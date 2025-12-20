package com.cars.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank(message = "Car name is required!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Car model is required!")
    @Column(nullable = false)
    private String model;

    private int manufactureYear;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false) // FK
    private Brand brand;

    @OneToOne
    @JoinColumn(name = "owner_id", unique = true)
    @JsonManagedReference
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "car_accessory",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private List<Accessory> accessories = new ArrayList<>();
}