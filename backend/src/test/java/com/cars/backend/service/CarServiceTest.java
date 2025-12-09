package com.cars.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarServiceTest {

    @Autowired
    CarService carService;

    @Test
    void scenario01() {
        boolean result = carService.checkCarData("Jeep Compass", 2006);
        assertTrue(result);
    }

    @Test
    void scenario02() {
        assertThrows(IllegalArgumentException.class, () -> {
            carService.checkCarData("Jeep Compass", 2005);
        });
    }
}
