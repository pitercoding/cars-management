package com.cars.backend.repository;

import com.cars.backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByDriversLicense(String driversLicense);

    @Query("SELECT o FROM Owner o WHERE o.car IS NULL")
    List<Owner> findAvailableOwners();
}
