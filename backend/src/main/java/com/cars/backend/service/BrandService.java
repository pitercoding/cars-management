package com.cars.backend.service;

import com.cars.backend.dto.BrandRequestDTO;
import com.cars.backend.dto.BrandResponseDTO;
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

    // ========== VALIDATION ==========
    public void checkBrandData(String name, String taxIdentificationNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Brand name cannot be null or empty.");
        }

        if (taxIdentificationNumber == null || taxIdentificationNumber.isBlank()) {
            throw new IllegalArgumentException("Tax identification number cannot be null or empty.");
        }
    }

    private BrandResponseDTO toResponseDTO(Brand brand) {
        return new BrandResponseDTO(brand.getId(), brand.getName(), brand.getTaxIdentificationNumber());
    }

    private Brand getBrandEntityById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Brand not found with id: " + id
                ));
    }

    // ========== CREATE ==========
    public BrandResponseDTO postBrand(BrandRequestDTO request) {
        checkBrandData(request.getName(), request.getTaxIdentificationNumber());

        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setTaxIdentificationNumber(request.getTaxIdentificationNumber());

        return toResponseDTO(brandRepository.save(brand));
    }

    // ========== READ ==========
    public List<BrandResponseDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public BrandResponseDTO getBrandById(Long id) {
        return toResponseDTO(getBrandEntityById(id));
    }

    // ========== UPDATE ==========
    public BrandResponseDTO updateBrand(BrandRequestDTO request, Long id) {
        checkBrandData(request.getName(), request.getTaxIdentificationNumber());

        Brand existing = getBrandEntityById(id);
        existing.setName(request.getName());
        existing.setTaxIdentificationNumber(request.getTaxIdentificationNumber());

        return toResponseDTO(brandRepository.save(existing));
    }

    // ========== DELETE ==========
    public void deleteBrand(Long id) {
        Brand existing = getBrandEntityById(id);
        brandRepository.delete(existing);
    }
}
