package com.cars.backend.controller;

import com.cars.backend.entity.Car;
import com.cars.backend.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // ========== CREATE ========== //
    @PostMapping
    public Car postCar(@RequestBody Car car) {
        return carService.postCar(car);
    }

    // ========== READ ========== //
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    // ========== UPDATE ========== //
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        return carService.updateCar(car, id);
    }

    // ========== DELETE ========== //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}

