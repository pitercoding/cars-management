package com.cars.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CarResponseDTO {
    private Long id;
    private String name;
    private String model;
    private int manufactureYear;
    private BrandResponseDTO brand;
    private OwnerResponseDTO owner;
    private List<AccessoryResponseDTO> accessories;
}
