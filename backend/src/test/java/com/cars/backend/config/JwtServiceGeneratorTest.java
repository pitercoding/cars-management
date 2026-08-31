package com.cars.backend.config;

import com.cars.backend.auth.User;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceGeneratorTest {

    private final JwtServiceGenerator jwtService = new JwtServiceGenerator();

    @BeforeEach
    void setUp() {
        // 256-bit base64 test-only key, never used outside this test.
        ReflectionTestUtils.setField(
                jwtService, "secretKey", "RwxTTbx7oXdX855ylJlPsuwBdQuXpqs+Txfa1Q9QAbI=");
    }

    private User someUser() {
        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setUsername("testuser");
        user.setPassword("hashed");
        user.setRole("ROLE_ADMIN");
        return user;
    }

    @Test
    void generateToken_producesANonBlankToken() {
        String token = jwtService.generateToken(someUser());

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractUsername_returnsTheSubjectFromTheToken() {
        String token = jwtService.generateToken(someUser());

        assertEquals("testuser", jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_returnsTrueForAFreshTokenAndMatchingUser() {
        User user = someUser();
        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void isTokenValid_returnsFalseWhenTheUsernameDoesNotMatch() {
        User tokenOwner = someUser();
        String token = jwtService.generateToken(tokenOwner);

        User someoneElse = someUser();
        someoneElse.setUsername("someone-else");

        assertFalse(jwtService.isTokenValid(token, someoneElse));
    }

    @Test
    void extractUsername_throwsOnAMalformedToken() {
        assertThrows(JwtException.class, () -> jwtService.extractUsername("not-a-real-jwt"));
    }
}
