package com.cars.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OwnerRequestDTO {
    @NotBlank(message = "Full name is required!")
    private String fullName;

    @NotNull(message = "Date of birth is required!")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Driver's license is required!")
    private String driversLicense;
}
