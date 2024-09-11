package com.example.demo;



import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testuser");
    }

    @Test
    void testFindByUsername() {
        // Given
        userRepository.save(user);

        // When
        Optional<User> result = userRepository.findByUsername("testuser");

        // Then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByUsername_NotFound() {
        // When
        Optional<User> result = userRepository.findByUsername("non-existent");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void testExistsByUsername() {
        // Given
        userRepository.save(user);

        // When
        boolean result = userRepository.existsByUsername("testuser");

        // Then
        assertTrue(result);
    }

    @Test
    void testExistsByUsername_NotFound() {
        // When
        boolean result = userRepository.existsByUsername("non-existent");

        // Then
        assertFalse(result);
    }
}