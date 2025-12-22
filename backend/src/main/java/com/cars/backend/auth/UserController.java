package com.cars.backend.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ========== CREATE ==========
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {
        User savedUser = userService.postUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // ========== READ ==========
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ========== UPDATE ==========
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(user, id);
    }

    // ========== DELETE ==========
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ========== PASSWORD ==========
    @PutMapping("/me/password")
    public ResponseEntity<Void> updateMyPassword(
            @Valid @RequestBody ChangePasswordDTO dto,
            Authentication auth) {

        User currentUser = (User) auth.getPrincipal();

        userService.updatePassword(currentUser.getId(), dto.getPassword());

        return ResponseEntity.ok().build();
    }

}

