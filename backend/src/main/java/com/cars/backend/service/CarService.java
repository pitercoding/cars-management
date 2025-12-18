package com.cars.backend.service;

import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    // ========== VALIDATION ========== //
    public boolean checkCarData(String name, int manufactureYear) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Car name cannot be null or empty.");
        }

        if ("Jeep Compass".equals(name) && manufactureYear < 2006) {
            throw new IllegalArgumentException(
                    "Jeep Compass cannot have a manufacture year earlier than 2006."
            );
        }
        return true;
    }

    // ========== CREATE ========== //
    public Car postCar(Car car) {
        checkCarData(car.getName(), car.getManufactureYear());

        if (car.getOwner() != null && car.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner must already exist.");
        }

        if (car.getAccessories() != null && !car.getAccessories().isEmpty()) {
            car.setAccessories(
                    car.getAccessories().stream()
                            .filter(a -> a.getId() != null)
                            .toList()
            );
        }

        return carRepository.save(car);
    }

    // ========== READ ========== //
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
    }

    // ========== UPDATE ========== //
    public Car updateCar(Car car, Long id) {
        checkCarData(car.getName(), car.getManufactureYear());
        Car existing = getCarById(id);

        existing.setName(car.getName());
        existing.setBrand(car.getBrand());
        existing.setModel(car.getModel());
        existing.setManufactureYear(car.getManufactureYear());
        existing.setAccessories(car.getAccessories());
        existing.setOwner(car.getOwner());

        return carRepository.save(existing);
    }

    // ========== DELETE ========== //
    public void deleteCar(Long id) {
        Car existing = getCarById(id);
        carRepository.delete(existing);
    }

    // ========== AUTOMATICALLY DERIVED QUERIES ========== //
    public List<Car> findByName(String name) {
        return carRepository.findByName(name);
    }

    public List<Car> findByBrandId(Long brandId) {
        return carRepository.findByBrandId(brandId);
    }

    public List<Car> findByManufactureYearGreaterThan(int manufactureYear) {
        return carRepository.findByManufactureYearGreaterThan(manufactureYear);
    }
}
