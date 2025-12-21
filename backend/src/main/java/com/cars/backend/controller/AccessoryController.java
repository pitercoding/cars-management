package com.cars.backend.controller;

import com.cars.backend.entity.Accessory;
import com.cars.backend.service.AccessoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accessories")
@CrossOrigin("http://localhost:4200")
public class AccessoryController {

    private final AccessoryService accessoryService;

    public AccessoryController(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<?> postAccessory(@Valid @RequestBody Accessory accessory) {
        try {
            Accessory savedAccessory = accessoryService.postAccessory(accessory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAccessory);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Accessory already exists.");
        }
    }

    // ========== READ ==========
    @GetMapping
    public List<Accessory> getAllAccessories() {
        return accessoryService.getAllAccessories();
    }

    @GetMapping("/{id}")
    public Accessory getAccessoryById(@PathVariable Long id) {
        return accessoryService.getAccessoryById(id);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public Accessory updateAccessory(@PathVariable Long id, @Valid @RequestBody Accessory accessory) {
        return accessoryService.updateAccessory(accessory, id);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
        return ResponseEntity.noContent().build();
    }
}

