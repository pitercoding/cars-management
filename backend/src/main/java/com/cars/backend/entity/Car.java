package com.cars.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String model;
    private int manufactureYear;

    public Car() {
    }

    public Car(String name, String brand, String model, int manufactureYear) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
    }
}