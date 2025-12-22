package com.cars.backend.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    @NotBlank(message = "Password cannot be blank!")
    private String password;
}
