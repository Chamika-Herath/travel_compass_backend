package com.travel.compass.controller;




//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.travel.compass.Dto.UserDTO;
//import com.travel.compass.model.User;
//import com.travel.compass.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//class AuthControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private HttpSession session;
//
//    @InjectMocks
//    private AuthController authController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void registerUser_Success() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("test@example.com");
//        when(userService.registerUser(any())).thenReturn(userDTO);
//
//        ResponseEntity<UserDTO> response = authController.register(userDTO);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("test@example.com", response.getBody().getEmail());
//    }
//
//    @Test
//    void login_Success() {
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setPassword("encodedPass");
//
//        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
//
//        UserDTO loginDTO = new UserDTO();
//        loginDTO.setEmail("test@example.com");
//        loginDTO.setPassword("password");
//
//        ResponseEntity<UserDTO> response = authController.login(loginDTO, session);
//
//        verify(session).setAttribute("user", user);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void login_InvalidCredentials() {
//        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());
//
//        UserDTO loginDTO = new UserDTO();
//        loginDTO.setEmail("test@example.com");
//        loginDTO.setPassword("wrong");
//
//        assertThrows(RuntimeException.class, () -> authController.login(loginDTO, session));
//    }
//
//    @Test
//    void getSessionUser_Authenticated() {
//        User user = new User();
//        user.setId(1L);
//        when(session.getAttribute("user")).thenReturn(user);
//        when(userService.getUserById(1L)).thenReturn(new UserDTO());
//
//        ResponseEntity<UserDTO> response = authController.getSessionUser(session);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void logout_Success() {
//        ResponseEntity<Void> response = authController.logout(session);
//        verify(session).invalidate();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}














import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.travel.compass.Dto.UserDTO;
import com.travel.compass.model.User;
import com.travel.compass.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class AuthControllerTest {


    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ... [existing mock declarations] ...

    @Test
    void registerUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        when(userService.registerUser(any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = authController.register(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("test@example.com", response.getBody().getEmail());
        System.out.println("✓ Registration successful - User created with status 201");
    }

    @Test
    void login_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPass");

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);

        UserDTO loginDTO = new UserDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        ResponseEntity<UserDTO> response = authController.login(loginDTO, session);

        verify(session).setAttribute("user", user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("✓ Login successful - Valid credentials accepted, session created");
    }

    @Test
    void login_InvalidCredentials() {
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());

        UserDTO loginDTO = new UserDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("wrong");

        assertThrows(RuntimeException.class, () -> authController.login(loginDTO, session));
        System.out.println("✓ Invalid login handled - Exception thrown for non-existent email");
    }

    @Test
    void getSessionUser_Authenticated() {
        User user = new User();
        user.setId(1L);
        when(session.getAttribute("user")).thenReturn(user);
        when(userService.getUserById(1L)).thenReturn(new UserDTO());

        ResponseEntity<UserDTO> response = authController.getSessionUser(session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("✓ Session validation successful - Authenticated user retrieved");
    }

    @Test
    void logout_Success() {
        ResponseEntity<Void> response = authController.logout(session);
        verify(session).invalidate();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("✓ Logout successful - Session invalidated");
    }
}