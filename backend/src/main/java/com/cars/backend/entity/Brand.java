package com.cars.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand name is required!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Tax Identification Number is required!")
    @Column(nullable = false, unique = true)
    private String taxIdentificationNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private List<Car> cars;

    // =============================
    // Constructor for testing only
    // =============================
    public Brand(String name, String taxIdentificationNumber) {
        this.name = name;
        this.taxIdentificationNumber = taxIdentificationNumber;
    }
}
