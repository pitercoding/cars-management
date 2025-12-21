package com.cars.backend.controller;

import com.cars.backend.entity.Brand;
import com.cars.backend.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin("http://localhost:4200")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<Brand> postBrand(@Valid @RequestBody Brand brand) {
        Brand savedBrand = brandService.postBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    // ========== READ ==========
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public Brand updateBrand(@PathVariable Long id, @Valid @RequestBody Brand brand) {
        return brandService.updateBrand(brand, id);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}

