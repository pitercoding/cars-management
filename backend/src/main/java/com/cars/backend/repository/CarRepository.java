package com.cars.backend.repository;

import com.cars.backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByName(String name);

    List<Car> findByBrandId(Long brandId);

    @Query("FROM Car c WHERE c.manufactureYear > :manufactureYear")
    List<Car> findByManufactureYearGreaterThan(int manufactureYear);
}
