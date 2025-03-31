//package com.travel.compass.controller;
//
//import com.travel.compass.model.User;
//import com.travel.compass.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // ✅ Update Profile (Change Name & Upload Profile Image)
//    @PutMapping("/{id}/update")
//    public ResponseEntity<User> updateProfile(
//            @PathVariable Long id,
//            @RequestParam String firstName,
//            @RequestParam String lastName,
//            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
//    ) {
//        try {
//            User updatedUser = userService.updateUserProfile(id, firstName, lastName, profileImage);
//            return ResponseEntity.ok(updatedUser);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    // ✅ Delete Account
//    @DeleteMapping("/{id}/delete")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }
//}
//
//
//
//



package com.travel.compass.controller;

import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Update user profile
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates,
            HttpSession session
    ) {
        User loggedInUser = (User) session.getAttribute("user");

        // Debug: Print session user and requested ID
        System.out.println("[DEBUG] Session User: " + loggedInUser);




        // Authorization check
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in!");
        }
        if (!loggedInUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own profile!");
        }

        try {
            User updatedUser = userService.updateUser(
                    id,
                    updates.get("firstName"),
                    updates.get("lastName"),
                    updates.get("email"),
                    updates.get("password")
            );

            // Refresh session data
            session.setAttribute("user", updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete user profile
    // Delete User Profile
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id,
            HttpSession session
    ) {
        User loggedInUser = (User) session.getAttribute("user");
        System.out.println("[DEBUG] Session User: " + loggedInUser);

        // Authorization check
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in!");
        }
        if (!loggedInUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own profile!");
        }

        userService.deleteUser(id);
        session.invalidate();
        return ResponseEntity.ok("Account deleted successfully!");
    }
}
