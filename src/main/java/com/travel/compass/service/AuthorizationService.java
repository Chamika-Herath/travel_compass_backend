package com.travel.compass.service;

import com.travel.compass.model.User;
import com.travel.compass.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserRepository userRepo;

    public AuthorizationService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void validateUserRole(Long userId, String requiredRole) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!requiredRole.equalsIgnoreCase(user.getRole())) {
            throw new AccessDeniedException(
                    "User ID " + userId + " does not have required role: " + requiredRole
            );
        }
    }
}