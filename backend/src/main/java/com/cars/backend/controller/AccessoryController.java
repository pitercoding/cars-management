package com.cars.backend.controller;

import com.cars.backend.dto.AccessoryRequestDTO;
import com.cars.backend.dto.AccessoryResponseDTO;
import com.cars.backend.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {

    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<AccessoryResponseDTO> postAccessory(@Valid @RequestBody AccessoryRequestDTO accessory) {
        AccessoryResponseDTO savedAccessory = accessoryService.postAccessory(accessory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccessory);
    }

    // ========== READ ==========
    @GetMapping
    public List<AccessoryResponseDTO> getAllAccessories() {
        return accessoryService.getAllAccessories();
    }

    @GetMapping("/{id}")
    public AccessoryResponseDTO getAccessoryById(@PathVariable Long id) {
        return accessoryService.getAccessoryById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public AccessoryResponseDTO updateAccessory(@PathVariable Long id, @Valid @RequestBody AccessoryRequestDTO accessory) {
        return accessoryService.updateAccessory(accessory, id);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}
