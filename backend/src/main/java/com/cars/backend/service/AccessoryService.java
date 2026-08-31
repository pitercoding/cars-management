package com.cars.backend.service;

import com.cars.backend.dto.AccessoryRequestDTO;
import com.cars.backend.dto.AccessoryResponseDTO;
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

    private AccessoryResponseDTO toResponseDTO(Accessory accessory) {
        return new AccessoryResponseDTO(accessory.getId(), accessory.getName());
    }

    private Accessory getAccessoryEntityById(Long id) {
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Accessory not found with id: " + id
                ));
    }

    // ========== CREATE ==========
    public AccessoryResponseDTO postAccessory(AccessoryRequestDTO request) {
        checkAccessoryData(request.getName());

        if (accessoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Accessory already exists.");
        }

        Accessory accessory = new Accessory();
        accessory.setName(request.getName());

        return toResponseDTO(accessoryRepository.save(accessory));
    }

    // ========== READ ==========
    public List<AccessoryResponseDTO> getAllAccessories() {
        return accessoryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AccessoryResponseDTO getAccessoryById(Long id) {
        return toResponseDTO(getAccessoryEntityById(id));
    }

    // ========== UPDATE ==========
    public AccessoryResponseDTO updateAccessory(AccessoryRequestDTO request, Long id) {
        checkAccessoryData(request.getName());

        Accessory existing = getAccessoryEntityById(id);

        if (!existing.getName().equalsIgnoreCase(request.getName())
                && accessoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Accessory already exists.");
        }

        existing.setName(request.getName());
        return toResponseDTO(accessoryRepository.save(existing));
    }

    // ========== DELETE ==========
    public void deleteAccessory(Long id) {
        Accessory existing = getAccessoryEntityById(id);
        accessoryRepository.delete(existing);
    }
}
