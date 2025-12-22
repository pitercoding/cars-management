package com.cars.backend.exception;

/**
 * Exception thrown when a car cannot be deleted
 * because it has related data (e.g., reservations, sales, etc.)
 */
public class CarDeletionException extends RuntimeException {
    private final String details;

    public CarDeletionException(String details) {
        super("Car cannot be deleted: " + details);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
