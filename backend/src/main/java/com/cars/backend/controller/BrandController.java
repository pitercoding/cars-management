package com.cars.backend.controller;

import com.cars.backend.dto.BrandRequestDTO;
import com.cars.backend.dto.BrandResponseDTO;
import com.cars.backend.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<BrandResponseDTO> postBrand(@Valid @RequestBody BrandRequestDTO brand) {
        BrandResponseDTO savedBrand = brandService.postBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    // ========== READ ==========
    @GetMapping
    public List<BrandResponseDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandResponseDTO getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public BrandResponseDTO updateBrand(@PathVariable Long id, @Valid @RequestBody BrandRequestDTO brand) {
        return brandService.updateBrand(brand, id);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
