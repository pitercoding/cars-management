package com.cars.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarRequestDTO {
    @NotBlank(message = "Car name is required!")
    private String name;

    @NotBlank(message = "Car model is required!")
    private String model;

    private int manufactureYear;

    @NotNull(message = "Brand is required!")
    private Long brandId;

    private Long ownerId;

    private List<Long> accessoryIds;
}
