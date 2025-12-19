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

    public User postUser(User user) {

        validateCreate(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // =========================
    // READ
    // =========================

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(u -> new UserResponseDTO(
                        u.getId(),
                        u.getFullName(),
                        u.getUsername(),
                        u.getRole()
                ))
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {

        User u = getUserEntityById(id);

        return new UserResponseDTO(
                u.getId(),
                u.getFullName(),
                u.getUsername(),
                u.getRole()
        );
    }

    // =========================
    // UPDATE
    // =========================

    public User updateUser(User user, Long id) {

        validateUpdate(user);

        User existing = getUserEntityById(id);

        existing.setFullName(user.getFullName());
        existing.setUsername(user.getUsername());
        existing.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existing);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteUser(Long id) {
        User existing = getUserEntityById(id);
        userRepository.delete(existing);
    }
}