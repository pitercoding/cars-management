package com.cars.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandResponseDTO {
    private Long id;
    private String name;
    private String taxIdentificationNumber;
}
