package com.cars.backend.service;

import com.cars.backend.dto.CarRequestDTO;
import com.cars.backend.dto.CarResponseDTO;
import com.cars.backend.entity.Accessory;
import com.cars.backend.entity.Brand;
import com.cars.backend.entity.Car;
import com.cars.backend.repository.AccessoryRepository;
import com.cars.backend.repository.BrandRepository;
import com.cars.backend.repository.CarRepository;
import com.cars.backend.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private AccessoryRepository accessoryRepository;

    @InjectMocks
    private CarService carService;

    private CarRequestDTO carRequest(String name, String model, int manufactureYear, Long brandId) {
        CarRequestDTO dto = new CarRequestDTO();
        dto.setName(name);
        dto.setModel(model);
        dto.setManufactureYear(manufactureYear);
        dto.setBrandId(brandId);
        return dto;
    }

    @Test
    void testPostCar_ValidCar() {
        Brand jeep = new Brand("Jeep", "55765432000149");
        jeep.setId(10L);

        when(brandRepository.findById(10L)).thenReturn(Optional.of(jeep));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> {
            Car saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        CarResponseDTO saved = carService.postCar(carRequest("Jeep Compass", "Sport", 2022, 10L));

        assertNotNull(saved.getId());
        assertEquals("Jeep Compass", saved.getName());
        assertEquals("Jeep", saved.getBrand().getName());
    }

    @Test
    void testPostCar_InvalidCar_ThrowsException() {
        CarRequestDTO request = carRequest("Jeep Compass", "Sport", 2005, 10L); // invalid: before 2006

        assertThrows(IllegalArgumentException.class, () -> carService.postCar(request));
        verifyNoInteractions(carRepository, brandRepository, ownerRepository, accessoryRepository);
    }

    @Test
    void testUpdateCar() {
        Brand fiat = new Brand("Fiat", "12345678901234");
        fiat.setId(5L);

        Car existing = new Car();
        existing.setId(1L);
        existing.setName("Fiat Uno");
        existing.setModel("Basic");
        existing.setManufactureYear(2010);
        existing.setBrand(fiat);

        when(carRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(brandRepository.findById(5L)).thenReturn(Optional.of(fiat));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CarResponseDTO updated = carService.updateCar(carRequest("Fiat Uno", "Sport", 2010, 5L), 1L);

        assertEquals("Sport", updated.getModel());
    }

    @Test
    void testUpdateCar_withEmptyAccessoryIds_leavesAMutableCollection() {
        // Regression test: resolveAccessories used to return List.of() for an
        // empty/null accessoryIds, which blew up with
        // UnsupportedOperationException when Hibernate called .clear() on it
        // while merging a detached Car in a real persistence context.
        Brand fiat = new Brand("Fiat", "12345678901234");
        fiat.setId(5L);

        Car existing = new Car();
        existing.setId(1L);
        existing.setName("Fiat Uno");
        existing.setModel("Basic");
        existing.setManufactureYear(2010);
        existing.setBrand(fiat);
        existing.setAccessories(new ArrayList<>(List.of(new Accessory())));

        CarRequestDTO request = carRequest("Fiat Uno", "Basic", 2010, 5L);
        request.setAccessoryIds(List.of());

        when(carRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(brandRepository.findById(5L)).thenReturn(Optional.of(fiat));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        carService.updateCar(request, 1L);

        assertDoesNotThrow(() -> existing.getAccessories().clear());
    }

    @Test
    void testDeleteCar() {
        Brand ford = new Brand("Ford", "98765432100123");
        Car car = new Car();
        car.setId(1L);
        car.setName("Ford Ka");
        car.setModel("Basic");
        car.setManufactureYear(2015);
        car.setBrand(ford);

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car))
                .thenReturn(Optional.empty());

        carService.deleteCar(1L);

        verify(carRepository).delete(car);
        assertThrows(EntityNotFoundException.class, () -> carService.getCarById(1L));
    }

    @Test
    void testFindByName() {
        Brand chevrolet = new Brand("Chevrolet", "11223344556677");
        Car car = new Car();
        car.setId(1L);
        car.setName("Chevrolet Onix");
        car.setModel("LT");
        car.setManufactureYear(2018);
        car.setBrand(chevrolet);

        when(carRepository.findByName("Chevrolet Onix")).thenReturn(List.of(car));

        List<CarResponseDTO> cars = carService.findByName("Chevrolet Onix");

        assertFalse(cars.isEmpty());
        assertEquals("Chevrolet Onix", cars.get(0).getName());
    }
}
