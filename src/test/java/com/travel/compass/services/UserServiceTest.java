package com.travel.compass.services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.travel.compass.Dto.UserDTO;
import com.travel.compass.model.User;
import com.travel.compass.repository.UserRepository;
import com.travel.compass.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_EmailExists_ThrowsException() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("exists@example.com");
        when(userRepository.findByEmail("exists@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> userService.registerUser(userDTO));
    }

    @Test
    void updateUser_PasswordEncoded() {
        User existingUser = new User();
        existingUser.setPassword("oldEncoded");
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("newPass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPass")).thenReturn("newEncoded");

        userService.updateUser(1L, userDTO);

        assertEquals("newEncoded", existingUser.getPassword());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
    }
}

