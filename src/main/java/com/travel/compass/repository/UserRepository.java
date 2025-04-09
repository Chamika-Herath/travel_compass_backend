package com.travel.compass.repository;

import com.travel.compass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Additional custom queries can be added here
    // Example: List<User> findByRole(String role);
}