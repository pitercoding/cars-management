package com.cars.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String username;
    private String role;
}
