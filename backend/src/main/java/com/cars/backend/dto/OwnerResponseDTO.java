package com.cars.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class OwnerResponseDTO {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String driversLicense;
    private int age;
}
