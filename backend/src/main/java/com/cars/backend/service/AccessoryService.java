package com.cars.backend.service;

import com.cars.backend.entity.Accessory;
import com.cars.backend.repository.AccessoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    // ========== VALIDATION ==========
    public void checkAccessoryData(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Accessory name cannot be null or empty.");
        }
    }

    // ========== CREATE ==========
    public Accessory postAccessory(Accessory accessory) {
        checkAccessoryData(accessory.getName());

        if (accessoryRepository.existsByNameIgnoreCase(accessory.getName())) {
            throw new IllegalArgumentException("Accessory already exists.");
        }

        return accessoryRepository.save(accessory);
    }

    // ========== READ ==========
    public List<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }

    public Accessory getAccessoryById(Long id) {
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Accessory not found with id: " + id
                ));
    }

    // ========== UPDATE ==========
    public Accessory updateAccessory(Accessory accessory, Long id) {
        checkAccessoryData(accessory.getName());

        Accessory existing = getAccessoryById(id);

        if (!existing.getName().equalsIgnoreCase(accessory.getName())
                && accessoryRepository.existsByNameIgnoreCase(accessory.getName())) {
            throw new IllegalArgumentException("Accessory already exists.");
        }

        existing.setName(accessory.getName());
        return accessoryRepository.save(existing);
    }

    // ========== DELETE ==========
    public void deleteAccessory(Long id) {
        Accessory existing = getAccessoryById(id);
        accessoryRepository.delete(existing);
    }
}