package com.cars.backend.repository;

import com.cars.backend.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    boolean existsByNameIgnoreCase(String name);
}
