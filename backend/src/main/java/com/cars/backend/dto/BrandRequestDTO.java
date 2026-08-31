package com.cars.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequestDTO {
    @NotBlank(message = "Brand name is required!")
    private String name;

    @NotBlank(message = "Tax Identification Number is required!")
    private String taxIdentificationNumber;
}
