package com.cars.backend.service;

import com.cars.backend.entity.Brand;
import com.cars.backend.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Validates business rules for Brand entities.
     * Ensures mandatory fields are present and applies
     * domain-specific constraints, such as preventing
     * @param name            the car's name
     * @param taxIdentificationNumber Brand's identification.
     * @return true if validation passes
     * @throws IllegalArgumentException if any business rule is violated
     */
    public boolean checkBrandData(String name, String taxIdentificationNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty.");
        }

        if (taxIdentificationNumber == null || taxIdentificationNumber.isBlank()) {
            throw new IllegalArgumentException("Tax identification number cannot be null or empty.");
        }
        return true;
    }

    // ========== CREATE ========== //
    public Brand postBrand(Brand brand) {
        checkBrandData(brand.getName(), brand.getTaxIdentificationNumber());
        return brandRepository.save(brand);
    }

    // ========== READ ========== //
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
    }

    // ========== UPDATE ========== //
    public Brand updateBrand(Brand brand, Long id) {
        checkBrandData(brand.getName(), brand.getTaxIdentificationNumber());
        Brand existing = getBrandById(id);

        existing.setName(brand.getName());
        existing.setTaxIdentificationNumber(brand.getTaxIdentificationNumber());

        return brandRepository.save(existing);
    }

    // ========== DELETE ========== //
    public void deleteBrand(Long id) {
        Brand existing = getBrandById(id);
        brandRepository.delete(existing);
    }
}
