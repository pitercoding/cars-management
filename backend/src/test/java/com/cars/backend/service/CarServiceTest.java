package com.cars.backend.service;

import com.cars.backend.entity.Brand;
import com.cars.backend.entity.Car;
import com.cars.backend.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void testPostCar_ValidCar() {
        Car car = new Car();
        car.setName("Jeep Compass");
        car.setModel("Sport");
        car.setManufactureYear(2022);
        car.setBrand(new Brand("Jeep", "55765432000149"));

        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> {
            Car saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Car saved = carService.postCar(car);

        assertNotNull(saved.getId());
        assertEquals("Jeep Compass", saved.getName());
        verify(carRepository).save(car);
    }

    @Test
    void testPostCar_InvalidCar_ThrowsException() {
        Car car = new Car();
        car.setName("Jeep Compass");
        car.setModel("Sport");
        car.setManufactureYear(2005); // Jeep Compass cannot be earlier than 2006
        car.setBrand(new Brand("Jeep", "55765432000149"));

        assertThrows(IllegalArgumentException.class, () -> carService.postCar(car));
        verifyNoInteractions(carRepository);
    }

    @Test
    void testUpdateCar() {
        Car existing = new Car();
        existing.setId(1L);
        existing.setName("Fiat Uno");
        existing.setModel("Basic");
        existing.setManufactureYear(2010);
        existing.setBrand(new Brand("Fiat", "12345678901234"));

        Car update = new Car();
        update.setName("Fiat Uno");
        update.setModel("Sport");
        update.setManufactureYear(2010);
        update.setBrand(existing.getBrand());

        when(carRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updated = carService.updateCar(update, 1L);

        assertEquals("Sport", updated.getModel());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Ford Ka");
        car.setModel("Basic");
        car.setManufactureYear(2015);
        car.setBrand(new Brand("Ford", "98765432100123"));

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car))
                .thenReturn(Optional.empty());

        carService.deleteCar(1L);

        verify(carRepository).delete(car);
        assertThrows(EntityNotFoundException.class, () -> carService.getCarById(1L));
    }

    @Test
    void testFindByName() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Chevrolet Onix");
        car.setModel("LT");
        car.setManufactureYear(2018);
        car.setBrand(new Brand("Chevrolet", "11223344556677"));

        when(carRepository.findByName("Chevrolet Onix")).thenReturn(List.of(car));

        List<Car> cars = carService.findByName("Chevrolet Onix");

        assertFalse(cars.isEmpty());
        assertEquals("Chevrolet Onix", cars.get(0).getName());
    }
}
