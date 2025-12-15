package com.cars.backend.controller;

import com.cars.backend.entity.Car;
import com.cars.backend.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin("http://localhost:4200")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // ========== CREATE ========== //
    @PostMapping
    public ResponseEntity<Car> postCar(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.postCar(car));
    }

    // ========== READ ========== //
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    // AUTOMATICALLY DERIVED QUERY
    @GetMapping("/findByName")
    public ResponseEntity<List<Car>> findByName(@RequestParam String name) {
        List<Car> carNameList = carService.findByName(name);
        return ResponseEntity.ok(carNameList);
    }

    // AUTOMATICALLY DERIVED QUERY
    @GetMapping("/findByBrand")
    public ResponseEntity<List<Car>> findByBrandId(@RequestParam Long brandId) {
        List<Car> carBrandList = carService.findByBrandId(brandId);
        return ResponseEntity.ok(carBrandList);
    }

    // AUTOMATICALLY DERIVED QUERY
    @GetMapping("/findByManufactureYearGreaterThan")
    public ResponseEntity<List<Car>> findByManufactureYearGreaterThan(@RequestParam int manufactureYear) {
        List<Car> carManufactureYear = carService.findByManufactureYearGreaterThan(manufactureYear);
        return ResponseEntity.ok(carManufactureYear);
    }

    // ========== READ by ID ========== //
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

