package com.travel.compass.service;

import com.travel.compass.Dto.UserDTO;
import com.travel.compass.model.User;
import com.travel.compass.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // User Registration
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // Get User by Email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Convert Entity to DTO
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    // Update User
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent ID change
        userDTO.setId(id);

        // Map only non-null fields from DTO to existing entity
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        // Explicitly exclude relationships from mapping
        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> {
                    mapper.skip(User::setId);
                    mapper.skip(User::setServiceRequests);
                    mapper.skip(User::setGuide);
                    mapper.skip(User::setHotelOwner);
                    mapper.skip(User::setVehicleProvider);
                });

        // Map the DTO to existing user
        modelMapper.map(userDTO, existingUser);

        // Handle password separately
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    // Get User by ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get All Users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Delete User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}