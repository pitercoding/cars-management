package com.cars.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "accessory")
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Accessory name is required!")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "accessories", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Car> cars = new ArrayList<>();
}
