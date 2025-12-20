package com.cars.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required!")
    @Column(nullable = false)
    private String fullName;

    @NotNull(message = "Date of birth is required!")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Driver's license is required!")
    @Column(name = "drivers_license", nullable = false, unique = true)
    private String driversLicense;

    @JsonIgnore
    @OneToOne(mappedBy = "owner")
    private Car car;

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}
