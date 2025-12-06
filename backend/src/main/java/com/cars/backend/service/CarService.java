package com.cars.backend.service;

import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public String save(Car car) {
        this.carRepository.save(car);
        return "Car saved!";
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

}
