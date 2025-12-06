package com.cars.backend.service;

import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public String save(Car car) {
        this.carRepository.save(car);
        return "Car saved!";
    }

    public Car findById(int id) {
        //TODO
        if (id == 1) {
            Car car = new Car();
            car.setName("Fusca");
            car.setBrand("Volkswagen");
            car.setModel("Volkswagen Typ 1");
            car.setManufactureYear(2010);
            return car;
        } else {
            throw new RuntimeException("Car not found with id: " + id);
        }
    }
}
