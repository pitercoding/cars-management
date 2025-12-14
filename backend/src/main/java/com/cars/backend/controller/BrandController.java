package com.cars.backend.controller;

import com.cars.backend.entity.Brand;
import com.cars.backend.entity.Car;
import com.cars.backend.service.BrandService;
import com.cars.backend.service.CarService;
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

    // ========== CREATE ========== //
    @PostMapping("/postBrand")
    public ResponseEntity<Brand> postBrand(@RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.postBrand(brand));
    }

    // ========== READ ========== //
    @GetMapping("/getAllBrands")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/getBrandById/{id}")
    public Brand getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    // ========== UPDATE ========== //
    @PutMapping("/updateBrand/{id}")
    public Brand updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        return brandService.updateBrand(brand, id);
    }

    // ========== DELETE ========== //
    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}

