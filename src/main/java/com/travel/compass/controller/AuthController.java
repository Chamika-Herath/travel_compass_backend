package com.travel.compass.controller;

import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> userMap) {
        String firstName = userMap.get("firstName");
        String lastName = userMap.get("lastName");
        String email = userMap.get("email");
        String password = userMap.get("password");

        userService.registerUser(firstName, lastName, email, password);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // User Login (Session-Based)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userMap, HttpSession session) {
        String email = userMap.get("email");
        String password = userMap.get("password");

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);

                // Return user details in response
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("id", user.getId());
                userDetails.put("firstName", user.getFirstName());
                userDetails.put("lastName", user.getLastName());
                userDetails.put("email", user.getEmail());
                userDetails.put("role", user.getRole());

                return ResponseEntity.ok(userDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
    }

    // Get Logged-in User Details
    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session!");
        }

        // Return user details
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmail());
        userDetails.put("role", user.getRole());

        return ResponseEntity.ok(userDetails);
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();  // Destroy session
        return ResponseEntity.ok("Logout successful!");
    }
}

