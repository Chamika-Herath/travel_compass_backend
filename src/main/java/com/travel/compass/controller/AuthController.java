package com.travel.compass.controller;




import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> userMap, HttpSession session) {
        String email = userMap.get("email");
        String password = userMap.get("password");

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            session.setAttribute("user", userOptional.get());  // Store user in session
            return "Login successful!";
        }
        return "Invalid email or password!";
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
