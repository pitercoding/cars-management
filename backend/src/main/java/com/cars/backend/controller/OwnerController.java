package com.cars.backend.controller;

import com.cars.backend.dto.OwnerRequestDTO;
import com.cars.backend.dto.OwnerResponseDTO;
import com.cars.backend.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<OwnerResponseDTO> postOwner(@Valid @RequestBody OwnerRequestDTO owner) {
        OwnerResponseDTO savedOwner = ownerService.postOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
    }

    // ========== READ ==========
    @GetMapping
    public List<OwnerResponseDTO> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @GetMapping("/{id}")
    public OwnerResponseDTO getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public OwnerResponseDTO updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequestDTO owner) {
        return ownerService.updateOwner(owner, id);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Available Owners ==========
    @GetMapping("/available")
    public List<OwnerResponseDTO> getAvailableOwners() {
        return ownerService.getAvailableOwners();
    }
}
