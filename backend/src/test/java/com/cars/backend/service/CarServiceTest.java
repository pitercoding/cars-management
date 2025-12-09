package com.cars.backend.service;

import com.cars.backend.entity.Brand;
import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

//    @BeforeEach
//    void setUp() {
//        carRepository.deleteAll();
//    }

    @Test
    void testPostCar_ValidCar() {
        Car car = new Car();
        car.setName("Jeep Compass");
        car.setModel("Sport");
        car.setManufactureYear(2022);
        car.setBrand(new Brand("Jeep", "55765432000149"));

        Car saved = carService.postCar(car);
        assertNotNull(saved.getId());
        assertEquals("Jeep Compass", saved.getName());
    }

    @Test
    void testPostCar_InvalidCar_ThrowsException() {
        Car car = new Car();
        car.setName("Jeep Compass");
        car.setModel("Sport");
        car.setManufactureYear(2005); // invalid
        car.setBrand(new Brand("Jeep", "55765432000149"));

        assertThrows(RuntimeException.class, () -> carService.postCar(car));
    }

    @Test
    void testUpdateCar() {
        Car car = new Car();
        car.setName("Fiat Uno");
        car.setModel("Basic");
        car.setManufactureYear(2010);
        car.setBrand(new Brand("Fiat", "12345678901234"));
        Car saved = carService.postCar(car);

        saved.setModel("Sport");
        Car updated = carService.updateCar(saved, saved.getId());

        assertEquals("Sport", updated.getModel());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setName("Ford Ka");
        car.setModel("Basic");
        car.setManufactureYear(2015);
        car.setBrand(new Brand("Ford", "98765432100123"));
        Car saved = carService.postCar(car);

        carService.deleteCar(saved.getId());
        assertThrows(RuntimeException.class, () -> carService.getCarById(saved.getId()));
    }

    @Test
    void testFindByName() {
        Car car = new Car();
        car.setName("Chevrolet Onix");
        car.setModel("LT");
        car.setManufactureYear(2018);
        car.setBrand(new Brand("Chevrolet", "11223344556677"));
        carService.postCar(car);

        List<Car> cars = carService.findByName("Chevrolet Onix");
        assertFalse(cars.isEmpty());
    }
}
