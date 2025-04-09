//package com.travel.compass.controller;
//
//import com.travel.compass.Dto.UserDTO;
//import com.travel.compass.model.User;
//import com.travel.compass.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @GetMapping
//    public List<UserDTO> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable Long id, HttpSession session) {
//        validateSessionUser(session, id);
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDTO> updateUser(
//            @PathVariable Long id,
//            @Valid @RequestBody UserDTO userDTO,
//            HttpSession session) {
//        validateSessionUser(session, id);
//        return ResponseEntity.ok(userService.updateUser(id, userDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpSession session) {
//        validateSessionUser(session, id);
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    private void validateSessionUser(HttpSession session, Long requestedUserId) {
//        User sessionUser = (User) session.getAttribute("user");
//        if (sessionUser == null || !sessionUser.getId().equals(requestedUserId)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized access");
//        }
//    }
//}


package com.travel.compass.controller;

import com.travel.compass.Dto.UserDTO;
import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Admin-only endpoint
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpSession session) {
        validateAdminRole(session);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Allow user to access their own profile or admin to access any
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id, HttpSession session) {
        validateUserAccess(session, id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO,
            HttpSession session) {
        validateUserAccess(session, id);
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            HttpSession session) {
        validateUserAccess(session, id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ========== Helper Methods ========== //

    private void validateUserAccess(HttpSession session, Long requestedUserId) {
        User sessionUser = getAuthenticatedUser(session);

        // Allow access if: same user OR admin
        if (!sessionUser.getId().equals(requestedUserId)) {
            validateAdminRole(session);
        }
    }

    private void validateAdminRole(HttpSession session) {
        User user = getAuthenticatedUser(session);
        if (!"ADMIN".equals(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin privileges required");
        }
    }

    private User getAuthenticatedUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        return user;
    }
}