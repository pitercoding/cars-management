package com.cars.backend.auth;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================
    // PRIVATE HELPERS
    // =========================

    private User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + id)
                );
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getRole()
        );
    }

    private void validateCreate(User user) {

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty.");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    private void validateUpdate(User user) {

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty.");
        }
    }

    // =========================
    // CREATE
    // =========================

    public UserResponseDTO postUser(User user) {

        validateCreate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return toResponseDTO(userRepository.save(user));
    }

    // =========================
    // READ
    // =========================

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        return toResponseDTO(getUserEntityById(id));
    }

    // =========================
    // UPDATE
    // =========================

    public UserResponseDTO updateUser(User user, Long id) {

        validateUpdate(user);

        User existing = getUserEntityById(id);

        existing.setFullName(user.getFullName());
        existing.setUsername(user.getUsername());
        existing.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return toResponseDTO(userRepository.save(existing));
    }

    // =========================
    // DELETE
    // =========================

    public void deleteUser(Long id) {
        User existing = getUserEntityById(id);
        userRepository.delete(existing);
    }

    // =========================
    // PASSWORD
    // =========================
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}