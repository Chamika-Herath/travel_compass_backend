//package com.travel.compass.service;
//
//import com.travel.compass.Dto.UserDTO;
//import com.travel.compass.model.User;
//import com.travel.compass.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.Conditions;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final ModelMapper modelMapper;
//
//    // User Registration
//    public UserDTO registerUser(UserDTO userDTO) {
//        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        User user = modelMapper.map(userDTO, User.class);
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        User savedUser = userRepository.save(user);
//        return convertToDto(savedUser);
//    }
//
//    // Get User by Email
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    // Convert Entity to DTO
//    public UserDTO convertToDto(User user) {
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    // Update User
//    @Transactional
//    public UserDTO updateUser(Long id, UserDTO userDTO) {
//        User existingUser = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Prevent ID change
//        userDTO.setId(id);
//
//        // Map only non-null fields from DTO to existing entity
//        modelMapper.getConfiguration()
//                .setSkipNullEnabled(true)
//                .setPropertyCondition(Conditions.isNotNull());
//
//        // Explicitly exclude relationships from mapping
//        modelMapper.typeMap(UserDTO.class, User.class)
//                .addMappings(mapper -> {
//                    mapper.skip(User::setId);
//                    mapper.skip(User::setServiceRequests);
//                    mapper.skip(User::setGuide);
//                    mapper.skip(User::setHotelOwner);
//                    mapper.skip(User::setVehicleProvider);
//                });
//
//        // Map the DTO to existing user
//        modelMapper.map(userDTO, existingUser);
//
//        // Handle password separately
//        if (userDTO.getPassword() != null) {
//            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }
//
//        User updatedUser = userRepository.save(existingUser);
//        return convertToDto(updatedUser);
//    }
//
//    // Get User by ID
//    public UserDTO getUserById(Long id) {
//        return userRepository.findById(id)
//                .map(this::convertToDto)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    // Get All Users
//    public List<UserDTO> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    // Delete User
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}




package com.travel.compass.service;

import com.travel.compass.Dto.ForgotPasswordRequestDTO;
import com.travel.compass.Dto.ResetPasswordRequestDTO;
import com.travel.compass.Dto.UserDTO;
import com.travel.compass.model.User;
import com.travel.compass.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userDTO.setId(id);
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> {
                    mapper.skip(User::setId);
                    mapper.skip(User::setServiceRequests);
                    mapper.skip(User::setGuide);
                    mapper.skip(User::setHotelOwner);
                    mapper.skip(User::setVehicleProvider);
                });

        modelMapper.map(userDTO, existingUser);

        if (userDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setResetPasswordToken(otp);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String emailText = "Your password reset OTP is: " + otp +
                "\nThis OTP is valid for 15 minutes.";
        emailService.sendEmail(email, "Password Reset Request", emailText);
    }

    public void resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if (!otp.equals(user.getResetPasswordToken())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (LocalDateTime.now().isAfter(user.getResetPasswordTokenExpiry())) {
            throw new RuntimeException("OTP has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);
    }
}