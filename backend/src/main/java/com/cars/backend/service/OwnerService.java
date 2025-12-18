package com.cars.backend.service;

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

    // ========== CREATE ==========
    public Owner postOwner(Owner owner) {
        checkOwnerData(owner.getFullName(), owner.getDateOfBirth(), owner.getDriversLicense());
        return ownerRepository.save(owner);
    }

    // ========== READ ==========
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Owner not found with id: " + id
                ));
    }

    // ========== UPDATE ==========
    public Owner updateOwner(Owner owner, Long id) {
        checkOwnerData(owner.getFullName(), owner.getDateOfBirth(), owner.getDriversLicense());

        Owner existing = getOwnerById(id);
        existing.setFullName(owner.getFullName());
        existing.setDateOfBirth(owner.getDateOfBirth());
        existing.setDriversLicense(owner.getDriversLicense());

        return ownerRepository.save(existing);
    }

    // ========== DELETE ==========
    public void deleteOwner(Long id) {
        Owner existing = getOwnerById(id);
        ownerRepository.delete(existing);
    }
}