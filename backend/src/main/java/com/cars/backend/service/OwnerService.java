package com.cars.backend.service;

import com.cars.backend.dto.OwnerRequestDTO;
import com.cars.backend.dto.OwnerResponseDTO;
import com.cars.backend.entity.Owner;
import com.cars.backend.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // ========== VALIDATION ==========
    public void checkOwnerData(String fullName, LocalDate dateOfBirth, String driversLicense) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty.");
        }

        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }

        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        if (age < 16) {
            throw new IllegalArgumentException("Owner must be at least 16 years old.");
        }
        if (age > 120) {
            throw new IllegalArgumentException("Owner age cannot be greater than 120 years.");
        }

        if (driversLicense == null || driversLicense.isBlank()) {
            throw new IllegalArgumentException("Driver's License cannot be null or empty.");
        }
    }

    private void checkDriversLicenseUnique(String driversLicense, Long currentOwnerId) {
        ownerRepository.findByDriversLicense(driversLicense).ifPresent(existing -> {
            if (currentOwnerId == null || !existing.getId().equals(currentOwnerId)) {
                throw new IllegalArgumentException("Driver's License already exists.");
            }
        });
    }

    private OwnerResponseDTO toResponseDTO(Owner owner) {
        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFullName(),
                owner.getDateOfBirth(),
                owner.getDriversLicense(),
                owner.getAge()
        );
    }

    private Owner getOwnerEntityById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Owner not found with id: " + id
                ));
    }

    // ========== CREATE ==========
    public OwnerResponseDTO postOwner(OwnerRequestDTO request) {
        checkOwnerData(request.getFullName(), request.getDateOfBirth(), request.getDriversLicense());
        checkDriversLicenseUnique(request.getDriversLicense(), null);

        Owner owner = new Owner();
        owner.setFullName(request.getFullName());
        owner.setDateOfBirth(request.getDateOfBirth());
        owner.setDriversLicense(request.getDriversLicense());

        return toResponseDTO(ownerRepository.save(owner));
    }

    // ========== READ ==========
    public List<OwnerResponseDTO> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OwnerResponseDTO getOwnerById(Long id) {
        return toResponseDTO(getOwnerEntityById(id));
    }

    // ========== UPDATE ==========
    public OwnerResponseDTO updateOwner(OwnerRequestDTO request, Long id) {
        checkOwnerData(request.getFullName(), request.getDateOfBirth(), request.getDriversLicense());
        checkDriversLicenseUnique(request.getDriversLicense(), id);

        Owner existing = getOwnerEntityById(id);
        existing.setFullName(request.getFullName());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setDriversLicense(request.getDriversLicense());

        return toResponseDTO(ownerRepository.save(existing));
    }

    // ========== DELETE ==========
    public void deleteOwner(Long id) {
        Owner existing = getOwnerEntityById(id);
        ownerRepository.delete(existing);
    }

    // ========== Available Owners ==========
    public List<OwnerResponseDTO> getAvailableOwners() {
        return ownerRepository.findAvailableOwners()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
