package com.cars.backend.service;

import com.cars.backend.dto.AccessoryResponseDTO;
import com.cars.backend.dto.BrandResponseDTO;
import com.cars.backend.dto.CarRequestDTO;
import com.cars.backend.dto.CarResponseDTO;
import com.cars.backend.dto.OwnerResponseDTO;
import com.cars.backend.entity.Accessory;
import com.cars.backend.entity.Brand;
import com.cars.backend.entity.Car;
import com.cars.backend.entity.Owner;
import com.cars.backend.exception.CarDeletionException;
import com.cars.backend.repository.AccessoryRepository;
import com.cars.backend.repository.BrandRepository;
import com.cars.backend.repository.CarRepository;
import com.cars.backend.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final OwnerRepository ownerRepository;
    private final AccessoryRepository accessoryRepository;

    public CarService(CarRepository carRepository,
                       BrandRepository brandRepository,
                       OwnerRepository ownerRepository,
                       AccessoryRepository accessoryRepository) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.ownerRepository = ownerRepository;
        this.accessoryRepository = accessoryRepository;
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

    // ========== MAPPING HELPERS ========== //
    private CarResponseDTO toResponseDTO(Car car) {
        BrandResponseDTO brandDTO = new BrandResponseDTO(
                car.getBrand().getId(), car.getBrand().getName(), car.getBrand().getTaxIdentificationNumber());

        OwnerResponseDTO ownerDTO = null;
        if (car.getOwner() != null) {
            Owner owner = car.getOwner();
            ownerDTO = new OwnerResponseDTO(
                    owner.getId(), owner.getFullName(), owner.getDateOfBirth(), owner.getDriversLicense(), owner.getAge());
        }

        List<AccessoryResponseDTO> accessoryDTOs = car.getAccessories().stream()
                .map(a -> new AccessoryResponseDTO(a.getId(), a.getName()))
                .toList();

        return new CarResponseDTO(
                car.getId(), car.getName(), car.getModel(), car.getManufactureYear(),
                brandDTO, ownerDTO, accessoryDTOs);
    }

    private Brand resolveBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + brandId));
    }

    private Owner resolveOwner(Long ownerId) {
        if (ownerId == null) {
            return null;
        }
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
    }

    private List<Accessory> resolveAccessories(List<Long> accessoryIds) {
        // Must stay mutable: Hibernate calls .clear() on this collection when
        // merging a detached entity, which fails against an immutable List.of().
        if (accessoryIds == null || accessoryIds.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(accessoryRepository.findAllById(accessoryIds));
    }

    // ========== CREATE ========== //
    public CarResponseDTO postCar(CarRequestDTO request) {
        checkCarData(request.getName(), request.getManufactureYear());

        Car car = new Car();
        car.setName(request.getName());
        car.setModel(request.getModel());
        car.setManufactureYear(request.getManufactureYear());
        car.setBrand(resolveBrand(request.getBrandId()));
        car.setOwner(resolveOwner(request.getOwnerId()));
        car.setAccessories(resolveAccessories(request.getAccessoryIds()));

        return toResponseDTO(carRepository.save(car));
    }

    // ========== READ ========== //
    public List<CarResponseDTO> getAllCars() {
        return carRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public Car getCarEntityById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
    }

    public CarResponseDTO getCarById(Long id) {
        return toResponseDTO(getCarEntityById(id));
    }

    // ========== UPDATE ========== //
    public CarResponseDTO updateCar(CarRequestDTO request, Long id) {
        checkCarData(request.getName(), request.getManufactureYear());
        Car existing = getCarEntityById(id);

        existing.setName(request.getName());
        existing.setModel(request.getModel());
        existing.setManufactureYear(request.getManufactureYear());
        existing.setBrand(resolveBrand(request.getBrandId()));
        existing.setOwner(resolveOwner(request.getOwnerId()));
        existing.setAccessories(resolveAccessories(request.getAccessoryIds()));

        return toResponseDTO(carRepository.save(existing));
    }

    // ========== DELETE ========== //
    public void deleteCar(Long id) {
        Car existing = getCarEntityById(id);

        if (existing.getOwner() != null || !existing.getAccessories().isEmpty()) {
            throw new CarDeletionException(
                    "This car cannot be deleted because it has related data (owner or accessories)."
            );
        }

        carRepository.delete(existing);
    }

    // ========== AUTOMATICALLY DERIVED QUERIES ========== //
    public List<CarResponseDTO> findByName(String name) {
        return carRepository.findByName(name).stream().map(this::toResponseDTO).toList();
    }

    public List<CarResponseDTO> findByBrandId(Long brandId) {
        return carRepository.findByBrandId(brandId).stream().map(this::toResponseDTO).toList();
    }

    public List<CarResponseDTO> findByManufactureYearGreaterThan(int manufactureYear) {
        return carRepository.findByManufactureYearGreaterThan(manufactureYear).stream().map(this::toResponseDTO).toList();
    }
}
