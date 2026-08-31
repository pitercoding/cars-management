package com.cars.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessoryRequestDTO {
    @NotBlank(message = "Accessory name is required!")
    private String name;
}
