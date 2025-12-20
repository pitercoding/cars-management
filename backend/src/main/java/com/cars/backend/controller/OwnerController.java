package com.cars.backend.controller;

import com.cars.backend.entity.Owner;
import com.cars.backend.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin("http://localhost:4200")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<?> postOwner(@Valid @RequestBody Owner owner) {
        try {
            Owner savedOwner = ownerService.postOwner(owner);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ========== READ ==========
    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner owner) {
        try {
            Owner updated = ownerService.updateOwner(owner, id);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Available Owners ==========
    @GetMapping("/available")
    public List<Owner> getAvailableOwners() {
        return ownerService.getAvailableOwners();
    }
}
