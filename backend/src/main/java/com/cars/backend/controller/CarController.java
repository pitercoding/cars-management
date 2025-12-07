package com.cars.backend.controller;

import com.cars.backend.entity.Car;
import com.cars.backend.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    // ========== LIST ALL ========== //
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    // ========== GET BY ID ========== //
    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id) {
        try {
            Car car = carService.getCarById(id);
            return ResponseEntity.ok(car);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ========== REGISTER NEW CAR ========== //
    @PostMapping
    public ResponseEntity<?> postCar(@RequestBody Car car) {
        try {
            String message = carService.postCar(car);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

