package com.cars.backend.service;

import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // ========== CREATE ========== //
    public Car postCar(Car car) {
        return carRepository.save(car);
    }

    // ========== READ ========== //
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    // ========== UPDATE ========== //
    public Car updateCar(Car car, Long id) {
        Car existing = getCarById(id);

        existing.setName(car.getName());
        existing.setBrand(car.getBrand());
        existing.setModel(car.getModel());
        existing.setManufactureYear(car.getManufactureYear());

        return carRepository.save(existing);
    }

    // ========== DELETE ========== //
    public void deleteCar(Long id) {
        Car existing = getCarById(id);
        carRepository.delete(existing);
    }

    // ========== AUTOMATICALLY DERIVED QUERIES ========== //
    public List<Car> findByName(String name){
        return carRepository.findByName(name);
    }

    public List<Car> findByBrandId(Long brandId){
        return carRepository.findByBrandId(brandId);
    }

    public List<Car> findByManufactureYearGreaterThan(int manufactureYear){
        return carRepository.findByManufactureYearGreaterThan(manufactureYear);
    }
}
