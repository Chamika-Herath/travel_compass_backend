package com.travel.compass.controller;

import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public String register(@RequestBody Map<String, String> userMap) {
        String firstName = userMap.get("firstName");
        String lastName = userMap.get("lastName");
        String email = userMap.get("email");
        String password = userMap.get("password");

        userService.registerUser(firstName, lastName, email, password);
        return "User registered successfully!";
    }

    // User Login (Session-Based)
    // User Login (Session-Based)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> userMap, HttpSession session) {
        String email = userMap.get("email");
        String password = userMap.get("password");

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);
                return ResponseEntity.ok("Login successful!");  // ✅ 200 OK for successful login
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!"); // ❌ 401 Unauthorized
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!"); // ❌ 401 Unauthorized
    }



    // Check Session (Get Logged-in User)
    @GetMapping("/session")
    public Object getSessionUser(HttpSession session) {
        Object user = session.getAttribute("user");
        return (user != null) ? user : "No active session!";
    }

    // Logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Destroy session
        return "Logout successful!";
    }
}
